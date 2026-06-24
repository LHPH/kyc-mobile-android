package com.kyc.mobile.data.remote.interceptors

import android.util.Base64
import android.util.Log
import com.kyc.mobile.BuildConfig
import com.kyc.mobile.data.annotation.Crypt
import com.kyc.mobile.data.remote.dto.EncryptedData
import com.kyc.mobile.data.util.ApiUtil.Companion.getAuthorization
import com.kyc.mobile.data.util.JsonDefaults
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

const val CRYPT_INTERCEPTOR_TAG = "CryptInterceptor"

class CryptInterceptor(
    private val propertyRepository: PropertiesRepository,
    private val aesCipher: AesCipher,
    private val rsaCipher: RsaCipher
): Interceptor {

    @Suppress("KotlinConstantConditions","SimplifyBooleanWithConstants")
    override fun intercept(chain: Interceptor.Chain): Response {

        val originalRequest = chain.request()
        val method = chain.request().tag(Invocation::class.java)!!.method()

        var secretKey: SecretKey? = null
        var publicKey: PublicKey?

        if(BuildConfig.ENCRYPTION_ENABLED && method.isAnnotationPresent(Crypt::class.java)){

            Log.d(CRYPT_INTERCEPTOR_TAG, "Encryption is enabled")
            val encryptedRequest = runBlocking {

                val publicKeyProperty = propertyRepository.getPropertyByKey(PropertyKeyEnum.KYC_GTW_PUBLIC_KEY.name)!!
                publicKey = rsaCipher.transformBase64BinaryKeyToPublicKey(publicKeyProperty.propertyValue!!)
                secretKey = aesCipher.createEphemeralKey()
                encryptRequestBody(originalRequest,secretKey,publicKey)
            }
            Log.d(CRYPT_INTERCEPTOR_TAG, "Calling service with encryption flow")
            val encryptedResponse = chain.proceed(encryptedRequest)
            return decryptResponseBody(encryptedResponse,secretKey!!)

        }
        return chain.proceed(originalRequest)
    }

    private fun encryptRequestBody(originalRequest: Request, secretKey: SecretKey, publicKey: PublicKey): Request{

        Log.d(CRYPT_INTERCEPTOR_TAG, "Encrypting request")
        val encodedAesKey = Base64.encodeToString(secretKey.encoded, Base64.NO_WRAP)
        Log.d(CRYPT_INTERCEPTOR_TAG, "Key: $encodedAesKey")
        val encryptedAesKey = rsaCipher.encrypt(encodedAesKey,publicKey)

        val requestBody = originalRequest.body
        val authorization = getAuthorization(originalRequest)
        var newRequestBody: RequestBody? = requestBody

        if(requestBody!=null){

            val requestBuffer = Buffer()
            requestBody.writeTo(requestBuffer)
            val originalRequestBody = requestBuffer.readUtf8()
            Log.d(CRYPT_INTERCEPTOR_TAG, "Request body before encryption: $originalRequestBody")
            val encryptedRequestBody = aesCipher.encrypt(originalRequestBody,secretKey)
            val encryptedData = EncryptedData(encryptedRequestBody)
            val encryptedDataStr = JsonDefaults.instance.encodeToString(encryptedData)
            newRequestBody = encryptedDataStr.toRequestBody(requestBody.contentType())
        }

        Log.d(CRYPT_INTERCEPTOR_TAG, "Authorization header before encryption: $authorization")
        val encryptedToken = aesCipher.encrypt(authorization,secretKey)

        return originalRequest.newBuilder()
            .header(HEADER_SESSION_KEY, encryptedAesKey)
            .header(HEADER_AUTHORIZATION, "$BEARER_TOKEN $encryptedToken")
            .method(originalRequest.method,newRequestBody)
            .build()
    }

    private fun decryptResponseBody(encryptedResponse: Response, secretKey: SecretKey): Response{

        Log.d(CRYPT_INTERCEPTOR_TAG, "Decrypting response")
        val responseBody = encryptedResponse.body
        val encryptedData = JsonDefaults.instance.decodeFromString<EncryptedData>(responseBody.string())
        val decryptedResponse = aesCipher.decrypt(encryptedData.data,secretKey)
        Log.d(CRYPT_INTERCEPTOR_TAG, "Response body after decryption: $decryptedResponse")
        return encryptedResponse.newBuilder()
            .body(decryptedResponse.toResponseBody(responseBody.contentType()))
            .build()
    }
}