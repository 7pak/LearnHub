package com.example.courseapp.data.remote.teacher_post_dto

import java.util.Date

data class LoginUserData(
    val email: String,
    val password: String,
    val type:String,
    val token:String?=null,
    val verification:Date?=null,
    val teacherId:String?=null
)