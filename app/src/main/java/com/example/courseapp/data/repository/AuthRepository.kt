package com.example.courseapp.data.repository

import com.example.courseapp.data.remote.EmailVerificationResponse
import com.example.courseapp.data.remote.LoginResponse
import com.example.courseapp.data.remote.StatusResponse
import com.example.courseapp.data.remote.RegisterResponse
import com.example.courseapp.data.remote.VerificationResponse
import com.example.courseapp.data.remote.teacher_post_dto.FieldsVerificationData
import com.example.courseapp.data.remote.teacher_post_dto.LoginUserData
import com.example.courseapp.data.remote.teacher_post_dto.VerificationData
import com.example.courseapp.data.remote.teacher_post_dto.RegisterUserData
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