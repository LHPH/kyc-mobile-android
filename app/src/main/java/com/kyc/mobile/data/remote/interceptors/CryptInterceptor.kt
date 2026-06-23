package com.kyc.mobile.data.remote.interceptors

import android.util.Base64
import android.util.Log
import com.kyc.mobile.BuildConfig
import com.kyc.mobile.data.annotation.Crypt
import com.kyc.mobile.data.util.ApiUtil.Companion.getAuthorization
import com.kyc.mobile.domain.security.AesCipher
import com.kyc.mobile.domain.security.RsaCipher
import com.kyc.mobile.domain.usecase.PropertiesRepository
import com.kyc.mobile.domain.util.AppConstants.BEARER_TOKEN
import com.kyc.mobile.domain.util.AppConstants.HEADER_AUTHORIZATION
import com.kyc.mobile.domain.util.AppConstants.HEADER_SESSION_KEY
import com.kyc.mobile.domain.util.PropertyKeyEnum
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import okio.Buffer
import retrofit2.Invocation
import java.security.PublicKey
import javax.crypto.SecretKey

class CryptInterceptor(
    private val propertyRepository: PropertiesRepository,
    private val aesCipher: AesCipher,
    private val rsaCipher: RsaCipher
): Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {

        val originalRequest = chain.request()
        val method = chain.request().tag(Invocation::class.java)!!.method()

        var secretKey: SecretKey? = null
        var publicKey: PublicKey?

        if(BuildConfig.ENCRYPTION_ENABLED && method.isAnnotationPresent(Crypt::class.java)){

            val encryptedRequest = runBlocking {

                val publicKeyProperty = propertyRepository.getPropertyByKey(PropertyKeyEnum.KYC_GTW_PUBLIC_KEY.name)!!
                publicKey = rsaCipher.transformBase64BinaryKeyToPublicKey(publicKeyProperty.propertyValue!!)
                secretKey = aesCipher.createEphemeralKey()
                encryptRequestBody(originalRequest,secretKey,publicKey)
            }

            val encryptedResponse = chain.proceed(encryptedRequest)
            return decryptResponseBody(encryptedResponse,secretKey!!)

        }
        return chain.proceed(originalRequest)
    }

    private fun encryptRequestBody(originalRequest: Request, secretKey: SecretKey, publicKey: PublicKey): Request{

        val encodedAesKey = Base64.encodeToString(secretKey.encoded, Base64.NO_WRAP)
        val encryptedAesKey = rsaCipher.encrypt(encodedAesKey,publicKey)

        val requestBody = originalRequest.body
        val authorization = getAuthorization(originalRequest)
        var newRequestBody: RequestBody? = requestBody

        if(requestBody!=null){

            val requestBuffer = Buffer()
            requestBody.writeTo(requestBuffer)
            val originalRequestBody = requestBuffer.readUtf8()
            Log.i("Encryptor",originalRequestBody)
            val encryptedRequestBody = aesCipher.encrypt(originalRequestBody,secretKey)
            newRequestBody = encryptedRequestBody.toRequestBody(requestBody.contentType())
        }

        val encryptedToken = aesCipher.encrypt(authorization,secretKey)

        return originalRequest.newBuilder()
            .header(HEADER_SESSION_KEY, encryptedAesKey)
            .header(HEADER_AUTHORIZATION, "$BEARER_TOKEN $encryptedToken")
            .method(originalRequest.method,newRequestBody)
            .build()
    }

    private fun decryptResponseBody(encryptedResponse: Response, secretKey: SecretKey): Response{

        val responseBody = encryptedResponse.body
        val decryptedResponse = aesCipher.decrypt(responseBody.string(),secretKey)

        return encryptedResponse.newBuilder()
            .body(decryptedResponse.toResponseBody(responseBody.contentType()))
            .build()
    }
}