package com.example.courseapp.data.remote.teacher_services

import com.example.courseapp.common.DataResponse
import com.example.courseapp.authentication.data.remote.auth_data.FieldsVerificationData
import com.example.courseapp.authentication.data.remote.auth_data.LoginUserData
import com.example.courseapp.authentication.data.remote.auth_data.RegisterUserData
import com.example.courseapp.authentication.data.remote.auth_data.VerificationData


typealias RegisterResponse = DataResponse<RegisterUserData>

typealias LoginResponse = DataResponse<LoginUserData>

typealias VerificationResponse = DataResponse<VerificationData>

typealias EmailVerificationResponse = DataResponse<FieldsVerificationData>