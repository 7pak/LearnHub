package com.example.courseapp.data.remote

import com.example.courseapp.data.remote.teacher_get_dto.CourseData
import com.example.courseapp.data.remote.teacher_get_dto.FileData
import com.example.courseapp.data.remote.teacher_get_dto.ProfileData
import com.example.courseapp.data.remote.teacher_get_dto.SectionData
import com.example.courseapp.data.remote.teacher_get_dto.VideoData
import com.example.courseapp.data.remote.teacher_post_dto.AddFileFields
import com.example.courseapp.data.remote.teacher_post_dto.AddSectionFields
import com.example.courseapp.data.remote.teacher_post_dto.AddVideoFields
import com.example.courseapp.data.remote.teacher_post_dto.AddCourseData
import com.example.courseapp.data.remote.teacher_post_dto.FieldsVerificationData
import com.example.courseapp.data.remote.teacher_post_dto.LoginUserData
import com.example.courseapp.data.remote.teacher_post_dto.VerificationData
import com.example.courseapp.data.remote.teacher_post_dto.RegisterUserData
import kotlinx.serialization.Serializable

@Serializable
data class DataResponse<T>(
    val data: T,
    val message: String,
    val status: Int
)

//Post data
typealias RegisterResponse = DataResponse<RegisterUserData>
typealias LoginResponse = DataResponse<LoginUserData>
typealias VerificationResponse = DataResponse<VerificationData>
typealias EmailVerificationResponse = DataResponse<FieldsVerificationData>
typealias AddCourseResponse = DataResponse<AddCourseData>
typealias AddSectionResponse = DataResponse<AddSectionFields>
typealias AddFileResponse = DataResponse<AddFileFields>
typealias AddVideoResponse = DataResponse<AddVideoFields>
///////////////////////////////////////////////////////////

//Get data
typealias GetCourseDataResponse = DataResponse<List<CourseData>>
typealias GetSectionResponse = DataResponse<List<SectionData>>
typealias GetVideoResponse = DataResponse<List<VideoData>>
typealias GetFileResponse = DataResponse<List<FileData>>
typealias GetProfileResponse = DataResponse<ProfileData>


