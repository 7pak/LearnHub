package com.example.courseapp.domain.repository

import com.example.courseapp.authentication.data.remote.AuthApi
import com.example.courseapp.authentication.data.remote.auth_data.FieldsVerificationData
import com.example.courseapp.authentication.data.remote.auth_data.LoginUserData
import com.example.courseapp.authentication.data.remote.auth_data.RegisterUserData
import com.example.courseapp.authentication.data.remote.auth_data.VerificationData
import com.example.courseapp.common.StatusResponse
import com.example.courseapp.data.remote.teacher_services.EmailVerificationResponse
import com.example.courseapp.data.remote.teacher_services.LoginResponse
import com.example.courseapp.data.remote.teacher_services.RegisterResponse
import com.example.courseapp.data.remote.teacher_services.VerificationResponse
import com.example.courseapp.data.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import retrofit2.Response
import javax.inject.Inject

class AuthRepositoryImp @Inject constructor(
    private val authApi: AuthApi,
    private val tokenFlow: Flow<String?>
) : AuthRepository {


    override suspend fun registerUser(registerUserData: RegisterUserData): Response<RegisterResponse> {
        return authApi.registerUser(registerUserData)
    }

    override suspend fun loginUser(loginUserData: LoginUserData): Response<LoginResponse> {
        return authApi.loginUser(loginUserData = loginUserData)
    }

    override suspend fun logoutUser(): Response<StatusResponse> {
        val token = tokenFlow.firstOrNull()
        return authApi.logoutUser("Bearer $token")
    }

    override suspend fun verifyOtp(verificationData: VerificationData): Response<VerificationResponse> {
        val token = tokenFlow.firstOrNull()
        return authApi.verifyUser("Bearer $token",verificationData)
    }

    override suspend fun resendOtp(): Response<StatusResponse> {
        val token = tokenFlow.firstOrNull()
        return authApi.resendOtp("Bearer $token")
    }

    override suspend fun accountVerification(fieldsVerificationData: FieldsVerificationData): Response<EmailVerificationResponse> {
        return authApi.accountVerification(fieldsVerificationData)
    }

    override suspend fun resetPassword(fieldsVerificationData: FieldsVerificationData): Response<StatusResponse> {
        val token = tokenFlow.firstOrNull()
        return authApi.resetPassword("Bearer $token",fieldsVerificationData)
    }


}