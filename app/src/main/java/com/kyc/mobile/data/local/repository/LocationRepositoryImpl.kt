package com.kyc.mobile.data.local.repository

import android.content.Context
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource
import com.kyc.mobile.domain.usecase.LocationRepository

class LocationRepositoryImpl(
    private val context: Context
): LocationRepository {

    private val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)

    override fun getLastLocation(onSuccess: (Pair<Double, Double>) -> Unit) {

        fusedLocationClient.getCurrentLocation(
            Priority.PRIORITY_HIGH_ACCURACY,
            CancellationTokenSource().token
        ).addOnSuccessListener { location ->
            location.let{
                onSuccess(Pair(it.latitude,it.longitude))
            }
        }
    }
}