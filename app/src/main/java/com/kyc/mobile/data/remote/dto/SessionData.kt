package com.kyc.mobile.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class SessionData(
    val owner: Long,
    val user: Long,
    val name: String? = "TEST",
    val channel: String,
    val role: String,
    val sub: String,
    val iss: String,
    val aud: List<String>,
    val headers: SessionHeadersData
)

@Serializable
data class SessionHeadersData(
    val alg: String
)
