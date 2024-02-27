package com.example.courseapp.teacher_features.data.repository


import com.example.courseapp.common.StatusResponse
import com.example.courseapp.teacher_features.data.remote.AddCourseResponse
import com.example.courseapp.teacher_features.data.remote.AddFileResponse
import com.example.courseapp.teacher_features.data.remote.AddSectionResponse
import com.example.courseapp.teacher_features.data.remote.AddVideoResponse
import com.example.courseapp.teacher_features.data.remote.GetCourseDataResponse
import com.example.courseapp.teacher_features.data.remote.GetFileResponse
import com.example.courseapp.teacher_features.data.remote.GetProfileResponse
import com.example.courseapp.teacher_features.data.remote.GetSectionResponse
import com.example.courseapp.teacher_features.data.remote.GetVideoResponse
import com.example.courseapp.teacher_features.data.remote.teacher_post_dto.AddSectionFields
import io.ktor.client.request.forms.MultiPartFormDataContent
import io.ktor.client.statement.HttpResponse
import okhttp3.MultipartBody
import retrofit2.Response

interface TeacherRepository {


    suspend fun deleteAccount():Response<StatusResponse>
    suspend fun addCourse(
        category_id: Int,
        description: String,
        price: String,
        title: String,
        photoPart: MultipartBody.Part?
    ): Response<AddCourseResponse>

    suspend fun updateCourse(
        id: Int,
        multipart: MultiPartFormDataContent
    ): HttpResponse

    suspend fun deleteCourse(
        id: Int
    ): Response<StatusResponse>

    suspend fun addSection(
        addSectionFields: AddSectionFields
    ): Response<AddSectionResponse>

    suspend fun updateSection(
        id: Int,
        addSectionFields: AddSectionFields
    ): Response<StatusResponse>

    suspend fun deleteSection(
        id: Int
    ): Response<StatusResponse>

    suspend fun addFile(
        sectionId: Int,
        filePart: MultipartBody.Part?
    ): Response<AddFileResponse>

    suspend fun updateFile(
        id: Int,
        multipart: MultiPartFormDataContent
    ): HttpResponse

    suspend fun deleteFile(
        id: Int
    ): Response<StatusResponse>

    suspend fun addVideo(
        sectionId: Int,
        title: String,
        visible: Int,
        videoUrl: MultipartBody.Part?
    ): Response<AddVideoResponse>

    suspend fun updateVideo(
        id: Int,
        multipart: MultiPartFormDataContent
    ): HttpResponse

    suspend fun deleteVideo(
        id: Int
    ): Response<StatusResponse>

    ///////////////////////////////////////////////////////


    suspend fun getProfileInfo(userId:Int): Response<GetProfileResponse>
    suspend fun getCourses(teacherId:Int): Response<GetCourseDataResponse>

    suspend fun getSections(
        id: Int,
        teacherId: Int
    ): Response<GetSectionResponse>

    suspend fun getFiles(
        id: Int,
        teacherId: Int
    ): Response<GetFileResponse>

    suspend fun getVideos(
        id: Int,
        teacherId: Int
    ): Response<GetVideoResponse>
}


