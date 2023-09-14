package com.example.courseapp.data.repository

import com.example.courseapp.data.remote.AddCourseResponse
import com.example.courseapp.data.remote.AddFileResponse
import com.example.courseapp.data.remote.AddSectionResponse
import com.example.courseapp.data.remote.AddVideoResponse
import com.example.courseapp.data.remote.GetCourseDataResponse
import com.example.courseapp.data.remote.GetFileResponse
import com.example.courseapp.data.remote.GetProfileResponse
import com.example.courseapp.data.remote.GetSectionResponse
import com.example.courseapp.data.remote.GetVideoResponse
import com.example.courseapp.data.remote.StatusResponse
import com.example.courseapp.data.remote.teacher_post_dto.AddSectionFields
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Part
import java.io.File

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
        description: String,
        price: String,
        title: String,
        category_id: Int,
        photoPart: File
    ): String?

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
        sectionId: Int,
        filePart: MultipartBody.Part?
    ): Response<StatusResponse>

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
       sectionId: Int,
        title: String,
        visible: Int,
        videoUrl: MultipartBody.Part?
    ): Response<StatusResponse>

    suspend fun deleteVideo(
        id: Int
    ): Response<StatusResponse>

    ///////////////////////////////////////////////////////


    suspend fun getProfileInfo(): Response<GetProfileResponse>
    suspend fun getCourses(): Response<GetCourseDataResponse>

    suspend fun getSections(
        id: Int
    ): Response<GetSectionResponse>

    suspend fun getVideos(
        id: Int
    ): Response<GetVideoResponse>

    suspend fun getFile(
        id: Int
    ): Response<GetFileResponse>
}


