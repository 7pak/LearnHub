package com.example.courseapp.authentication.presentation.common_item

import com.example.courseapp.navigation.UserType

data class AuthState(
    val name: String = "",
    val email: String = "",
    val password: String = "",
    val passwordConfirmation: String = "",
    val userType:UserType=UserType.STUDENT
)
