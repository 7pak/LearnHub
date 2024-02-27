package com.example.courseapp.common

import kotlinx.serialization.Serializable

@Serializable
data class StatusResponse(
    val message:String,
    val status:Int,
)

@Serializable
data class DataResponse<T>(
    val data: T,
    val message: String,
    val status: Int
)
