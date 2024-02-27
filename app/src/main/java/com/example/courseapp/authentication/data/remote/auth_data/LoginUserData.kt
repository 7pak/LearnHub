package com.example.courseapp.authentication.data.remote.auth_data

import java.util.Date


data class LoginUserData(
    val email: String,
    val password: String,
    val type:String,
    val token:String?=null,
    val verification:Date?=null,
    val id:Int?=null
)