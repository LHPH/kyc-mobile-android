package com.kyc.mobile.data.util

open class SingletonHolder<out T, in A>(
    private val constructor: (A)-> T
) {
    @Volatile
    private var instance: T? = null

    fun getInstance(args: A): T{
        return instance ?: synchronized(this){
            instance ?: constructor(args).also{
                instance = it
            }
        }
    }
}