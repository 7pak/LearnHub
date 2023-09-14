package com.example.courseapp.data.remote.teacher_post_dto

data class VerificationData(
    val otp:Int,
    val token:String?=null
)
