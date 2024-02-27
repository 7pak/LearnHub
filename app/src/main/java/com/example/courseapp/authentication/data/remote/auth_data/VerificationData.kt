package com.example.courseapp.authentication.data.remote.auth_data

data class VerificationData(
    val code:Int,
    val token:String?=null
)
