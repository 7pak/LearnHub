package com.example.courseapp.authentication.data.remote.auth_data


data class RegisterUserData(
    val name:String,
    val email: String,
    val password: String,
    val type:String,
    val verification:String?=null,
    val token: String?=null
)