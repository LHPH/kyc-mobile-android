package com.kyc.mobile.di

import com.kyc.mobile.BuildConfig
import com.kyc.mobile.data.remote.api.AuthApi
import com.kyc.mobile.data.remote.api.CustomerApplicationApi
import com.kyc.mobile.data.remote.api.CustomerBillsApi
import com.kyc.mobile.data.remote.api.CustomerTrackActionApi
import com.kyc.mobile.data.remote.api.NotificationsApi
import com.kyc.mobile.data.remote.api.OfferApi
import com.kyc.mobile.data.remote.api.PublicApi
import com.kyc.mobile.data.remote.interceptors.CryptInterceptor
import com.kyc.mobile.data.remote.interceptors.JwtInterceptor
import com.kyc.mobile.data.util.JsonDefaults
import okhttp3.CertificatePinner
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import retrofit2.create

interface NetworkModule {
    val authApi: AuthApi
    val customerApplicationApi: CustomerApplicationApi
    val customerTrackActionApi: CustomerTrackActionApi
    val notificationApi: NotificationsApi
    val offerApi: OfferApi
    val customerBillsApi: CustomerBillsApi
    val publicApi: PublicApi
}

class NetworkModuleImpl(
    private val dataStoreModule: DataStoreModule,
    private val firebaseModule: FirebaseModule,
    private val securityModule: SecurityModule
): NetworkModule{

    val contentType = "application/json".toMediaType()
    val okHttpClient: OkHttpClient by lazy {
        buildOkHttpClient()
    }

    override val authApi: AuthApi by lazy{
            getRetrofit()
            .create()
    }

    override val customerApplicationApi: CustomerApplicationApi by lazy{
            getRetrofit()
            .create()
    }

    override val customerTrackActionApi: CustomerTrackActionApi by lazy{
            getRetrofit()
            .create()
    }

    override val notificationApi: NotificationsApi by lazy{
            getRetrofit()
            .create()
    }

    override val offerApi: OfferApi by lazy{
            getRetrofit()
            .create()
    }

    override val customerBillsApi: CustomerBillsApi by lazy{
            getRetrofit()
            .create()
    }

    override val publicApi: PublicApi by lazy{
            getRetrofit()
            .create()
    }

    private fun getRetrofit(): Retrofit{
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(JsonDefaults.instance.asConverterFactory(contentType))
            .client(okHttpClient)
            .build()
    }

    @Suppress("KotlinConstantConditions")
    private fun buildOkHttpClient(): OkHttpClient{

        val hostname = BuildConfig.HOSTNAME

        val certificatePinner = if(BuildConfig.SSL_ENABLED){

            val pin = firebaseModule.remoteConfigManager.getConfigStringValue("ssl_pin_primary")
            CertificatePinner.Builder()
                .add(hostname,pin)
                .build()
        }
        else{
            CertificatePinner.DEFAULT
        }

        return  OkHttpClient.Builder()
            .addInterceptor(jwtInterceptor())
            .addInterceptor(cryptInterceptor())
            .addInterceptor(loggingInterceptor())
            .certificatePinner(certificatePinner)
            .build()
    }

    @Suppress("KotlinConstantConditions")
    private fun loggingInterceptor(): Interceptor{

        val loggingInterceptor = HttpLoggingInterceptor().apply {
            if(BuildConfig.LOG_ENABLED){
                level = HttpLoggingInterceptor.Level.BODY
            }
            else{
                level = HttpLoggingInterceptor.Level.NONE
            }
        }
        return loggingInterceptor;
    }

    private fun jwtInterceptor(): JwtInterceptor{
        return JwtInterceptor(dataStoreModule.dataStoreRepository)
    }

    private fun cryptInterceptor(): CryptInterceptor{
        return CryptInterceptor(
            dataStoreModule.propertiesRepository,
            securityModule.aesCipher,
            securityModule.rsaCipher
        )
    }
}