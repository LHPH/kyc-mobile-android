package com.kyc.mobile.ui.navigation

import androidx.navigation.NavType
import androidx.savedstate.SavedState
import kotlinx.serialization.json.Json

inline fun <reified T> createNavType() : NavType<T> {

    return object: NavType<T>(isNullableAllowed = true){
        override fun put(bundle: SavedState, key: String, value: T){
            bundle.putString(key,serializeAsValue(value))
        }

        override fun get(bundle: SavedState, key: String): T? {
            return bundle.getString(key)?.let{
                parseValue(it)
            }
        }

        override fun parseValue(value: String): T = Json.decodeFromString<T>(value)

        override fun serializeAsValue(value: T): String = Json.encodeToString(value)
    }
}