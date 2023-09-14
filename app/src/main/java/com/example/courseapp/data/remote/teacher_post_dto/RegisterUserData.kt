package com.example.courseapp.data.remote.teacher_post_dto


data class RegisterUserData(
    val name:String,
    val email: String,
    val password: String,
    val type:String,
    val verification:String?=null,
    val token: String?=null
)