package com.example.courseapp.data.repository

import com.example.courseapp.authentication.data.remote.auth_data.FieldsVerificationData
import com.example.courseapp.authentication.data.remote.auth_data.LoginUserData
import com.example.courseapp.authentication.data.remote.auth_data.RegisterUserData
import com.example.courseapp.authentication.data.remote.auth_data.VerificationData
import com.example.courseapp.common.StatusResponse
import com.example.courseapp.data.remote.teacher_services.EmailVerificationResponse
import com.example.courseapp.data.remote.teacher_services.LoginResponse
import com.example.courseapp.data.remote.teacher_services.RegisterResponse
import com.example.courseapp.data.remote.teacher_services.VerificationResponse
import retrofit2.Response

interface AuthRepository {
    suspend fun registerUser(registerUserData: RegisterUserData): Response<RegisterResponse>

    suspend fun loginUser(loginUserData: LoginUserData): Response<LoginResponse>

    suspend fun logoutUser():Response<StatusResponse>

    suspend fun verifyOtp(verificationData: VerificationData):Response<VerificationResponse>
    suspend fun resendOtp():Response<StatusResponse>

    suspend fun accountVerification(fieldsVerificationData: FieldsVerificationData):Response<EmailVerificationResponse>
    suspend fun resetPassword(fieldsVerificationData: FieldsVerificationData):Response<StatusResponse>
}