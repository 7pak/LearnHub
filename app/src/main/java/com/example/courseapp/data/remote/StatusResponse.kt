package com.example.courseapp.data.remote

import kotlinx.serialization.Serializable

@Serializable
data class StatusResponse(
    val message:String,
    val status:Int,
)
