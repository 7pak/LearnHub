package com.example.courseapp.domain.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.courseapp.data.remote.EmailVerificationResponse
import com.example.courseapp.data.remote.LoginResponse
import com.example.courseapp.data.remote.StatusResponse
import com.example.courseapp.data.remote.RegisterResponse
import com.example.courseapp.data.remote.VerificationResponse
import com.example.courseapp.data.remote.teacher_post_dto.FieldsVerificationData
import com.example.courseapp.data.remote.teacher_post_dto.LoginUserData
import com.example.courseapp.data.remote.teacher_post_dto.VerificationData
import com.example.courseapp.data.remote.teacher_post_dto.RegisterUserData
import com.example.courseapp.domain.repository.AuthRepositoryImp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class AuthModel @Inject constructor(
    private val authRepositoryImp: AuthRepositoryImp,
):ViewModel() {

    private val _registerResponse = MutableLiveData<Response<RegisterResponse>>()
    val registerResponse : LiveData<Response<RegisterResponse>> = _registerResponse

    private val _loginResponse = MutableLiveData<Response<LoginResponse>>()
    val loginResponse : LiveData<Response<LoginResponse>> = _loginResponse

    private var _logoutResponse = MutableLiveData<Response<StatusResponse>>()
    val logoutResponse: LiveData<Response<StatusResponse>> = _logoutResponse

    private var _otpVerificationResponse = MutableLiveData<Response<VerificationResponse>>()
    val otpVerificationResponse: LiveData<Response<VerificationResponse>> = _otpVerificationResponse

    private var _resendOtpResponse = MutableLiveData<Response<StatusResponse>>()
    val resendOtpResponse: LiveData<Response<StatusResponse>> = _resendOtpResponse

    private var _accountVerificationResponse = MutableLiveData<Response<EmailVerificationResponse>>()
    val accountVerificationResponse: LiveData<Response<EmailVerificationResponse>> = _accountVerificationResponse

    private var _resetPasswordResponse = MutableLiveData<Response<StatusResponse>>()
    val resetPasswordResponse: LiveData<Response<StatusResponse>> = _resetPasswordResponse


    fun registerUser(registerUserData: RegisterUserData) {
        viewModelScope.launch {
            val response = withContext(Dispatchers.IO) {
                authRepositoryImp.registerUser(registerUserData = registerUserData)
            }
            _registerResponse.value = response
        }
    }

    fun loginUser(loginUserData: LoginUserData) {
        viewModelScope.launch {
            val response = withContext(Dispatchers.IO) {
                authRepositoryImp.loginUser(loginUserData = loginUserData)
            }
            _loginResponse.value = response
        }
    }

    fun logoutUser() {
        viewModelScope.launch {
            _logoutResponse.value = authRepositoryImp.logoutUser()
        }
    }

    fun verifyUser(verificationData: VerificationData) {
        viewModelScope.launch {
            _otpVerificationResponse.value = authRepositoryImp.verifyOtp(verificationData=verificationData)
        }
    }

    fun resendOtp() {
        viewModelScope.launch {
            _resendOtpResponse.value = authRepositoryImp.resendOtp()
        }
    }


    fun accountVerification(fieldsVerificationData: FieldsVerificationData) {
        viewModelScope.launch {
            _accountVerificationResponse.value = authRepositoryImp.accountVerification(fieldsVerificationData = fieldsVerificationData)
        }
    }

    fun resetPassword(fieldsVerificationData: FieldsVerificationData) {
        viewModelScope.launch {
            _resetPasswordResponse.value = authRepositoryImp.resetPassword(fieldsVerificationData)
        }
    }
}