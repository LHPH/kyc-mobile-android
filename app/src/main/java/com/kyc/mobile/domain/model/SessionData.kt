package com.kyc.mobile.domain.model

data class SessionData(
    val owner: Long,
    val user: Long,
    val name: String?,
    val channel: String,
    val role: String,
    val sub: String,
    val iss: String,
    val aud: List<String>,
    val headers: SessionHeadersData
)

data class SessionHeadersData(
    val alg: String
)
