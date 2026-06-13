package com.kyc.mobile.data.remote.repository

import android.util.Log
import com.kyc.mobile.data.remote.api.PublicApi
import com.kyc.mobile.data.remote.handlingApiResponse
import com.kyc.mobile.domain.usecase.PublicRepository


class PublicRepositoryImpl(
    private val publicApi: PublicApi
): PublicRepository{


    override suspend fun getPublicKey(): String {

        val result = handlingApiResponse {
            publicApi.getPublicKey()
        }
            .onSuccess {
                Log.i("PublicRepositoryImpl","Successfully get public key")
            }
            .onFailure {throwable ->
                Log.e("PublicRepositoryImpl","Exception $throwable")
            }

        val responseData = result.getOrThrow()
        return responseData.data!!
    }
}