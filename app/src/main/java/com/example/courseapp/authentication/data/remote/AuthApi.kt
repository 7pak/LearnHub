package com.example.courseapp.authentication.data.remote

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
import retrofit2.http.*

interface AuthApi {
    @POST("/api/v1/register")
    suspend fun registerUser(
        @Body registerUserData: RegisterUserData
    ): Response<RegisterResponse>


    @POST("/api/v1/login")
    suspend fun loginUser(
        @Body loginUserData: LoginUserData
    ): Response<LoginResponse>

    @POST("/api/v1/verify-otp")
    suspend fun verifyUser(
        @Header("Authorization") token: String,
        @Body verificationData: VerificationData
    ): Response<VerificationResponse>

    @POST("/api/v1/resend-otp")
    suspend fun resendOtp(@Header("Authorization") token: String): Response<StatusResponse>


    @POST("/api/v1/password/verification")
    suspend fun accountVerification(@Body fieldsVerificationData: FieldsVerificationData): Response<EmailVerificationResponse>

    @POST("api/v1/password/reset")
    suspend fun resetPassword(
        @Header("Authorization") token: String,
        @Body fieldsVerificationData: FieldsVerificationData
    ): Response<StatusResponse>


    @POST("/api/v1/teacher/logout")
    suspend fun logoutUser(@Header("Authorization") token: String): Response<StatusResponse>
}
