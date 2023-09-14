package com.example.courseapp.domain.repository

import com.example.courseapp.data.remote.CourseApi
import com.example.courseapp.data.remote.EmailVerificationResponse
import com.example.courseapp.data.remote.LoginResponse
import com.example.courseapp.data.remote.StatusResponse
import com.example.courseapp.data.remote.RegisterResponse
import com.example.courseapp.data.remote.VerificationResponse
import com.example.courseapp.data.remote.teacher_post_dto.FieldsVerificationData
import com.example.courseapp.data.remote.teacher_post_dto.LoginUserData
import com.example.courseapp.data.remote.teacher_post_dto.VerificationData
import com.example.courseapp.data.remote.teacher_post_dto.RegisterUserData
import com.example.courseapp.data.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import retrofit2.Response
import javax.inject.Inject

class AuthRepositoryImp @Inject constructor(
    private val courseApi: CourseApi,
    private val tokenFlow: Flow<String?>
) : AuthRepository {


    override suspend fun registerUser(registerUserData: RegisterUserData): Response<RegisterResponse> {
        return courseApi.registerUser(registerUserData)
    }

    override suspend fun loginUser(loginUserData: LoginUserData): Response<LoginResponse> {
        return courseApi.loginUser(loginUserData = loginUserData)
    }

    override suspend fun logoutUser(): Response<StatusResponse> {
        val token = tokenFlow.firstOrNull()
        return courseApi.logoutUser("Bearer $token")
    }

    override suspend fun verifyOtp(verificationData: VerificationData): Response<VerificationResponse> {
        val token = tokenFlow.firstOrNull()
        return courseApi.verifyUser("Bearer $token",verificationData)
    }

    override suspend fun resendOtp(): Response<StatusResponse> {
        val token = tokenFlow.firstOrNull()
        return courseApi.resendOtp("Bearer $token")
    }

    override suspend fun accountVerification(fieldsVerificationData: FieldsVerificationData): Response<EmailVerificationResponse> {
        return courseApi.accountVerification(fieldsVerificationData)
    }

    override suspend fun resetPassword(fieldsVerificationData: FieldsVerificationData): Response<StatusResponse> {
        val token = tokenFlow.firstOrNull()
        return courseApi.resetPassword("Bearer $token",fieldsVerificationData)
    }


}