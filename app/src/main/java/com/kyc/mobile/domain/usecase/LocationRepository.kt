package com.kyc.mobile.domain.usecase


interface LocationRepository {

    fun getLastLocation(onSuccess:(Pair<Double,Double>) ->Unit)
}