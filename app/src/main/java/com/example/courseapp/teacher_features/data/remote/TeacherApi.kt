package com.example.courseapp.teacher_features.data.remote

import com.example.courseapp.common.StatusResponse
import com.example.courseapp.teacher_features.data.remote.teacher_post_dto.AddSectionFields
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*

interface TeacherApi {

//    @PUT("/api/v1/teacher/{id}")
//    suspend fun deleteAccount(
//        @Header("Authorization") token: String,
//    ):Response<StatusResponse>

    @DELETE("/api/v1/teacher/destroy")
    suspend fun deleteAccount(
        @Header("Authorization") token: String,
    ):Response<StatusResponse>


    @Multipart
    @POST("/api/v1/courses")
    suspend fun addCourse(
        @Header("Authorization") token: String,
        @Part photo: MultipartBody.Part?,
        @Part("title") title: RequestBody,
        @Part("description") description: RequestBody,
        @Part("category_id") categoryId: Int,
        @Part("price") price: RequestBody
    ): Response<AddCourseResponse>

    @DELETE("/api/v1/courses/{id}")
    suspend fun deleteCourse(
        @Header("Authorization") token: String,
        @Path("id") id: Int,
    ): Response<StatusResponse>



    @POST("/api/v1/courses/sections")
    suspend fun addSection(
        @Header("Authorization") token: String,
        @Body addSectionFields: AddSectionFields
    ): Response<AddSectionResponse>

    @PUT("/api/v1/courses/sections/{id}")
    suspend fun updateSection(
        @Header("Authorization") token: String,
        @Path("id") id: Int, // Replace Int with the appropriate data type for your section ID
        @Body addSectionFields: AddSectionFields // Define this data class as needed
    ): Response<StatusResponse>

    @DELETE("/api/v1/courses/sections/{id}")
    suspend fun deleteSection(
        @Header("Authorization") token: String,
        @Path("id") id: Int,
    ): Response<StatusResponse>


    @Multipart
    @POST("/api/v1/sections/files")
    suspend fun addFile(
        @Header("Authorization") token: String,
        @Part fileUrl: MultipartBody.Part?,
        @Part("section_id") section_id: Int
        ): Response<AddFileResponse>

    @DELETE("/api/v1/sections/files/{id}")
    suspend fun deleteFile(
        @Header("Authorization") token: String,
        @Path("id") id: Int,
    ): Response<StatusResponse>


    @Multipart
    @POST("/api/v1/sections/videos")
    suspend fun addVideo(
        @Header("Authorization") token: String,
        @Part videoUrl: MultipartBody.Part?,
        @Part("title") title: RequestBody,
        @Part("visible") visible: Int,
        @Part("section_id") section_id: Int
    ): Response<AddVideoResponse>

    @DELETE("/api/v1/sections/videos/{id}")
    suspend fun deleteVideo(
        @Header("Authorization") token: String,
        @Path("id") id: Int,
    ): Response<StatusResponse>


    /////////////////////////////////////////////////////////////////////////////////////////////

    @GET("/api/v1/teacher/profile")
    suspend fun getProfileInfo(
        @Header("Authorization") token: String,
        @Query("teacher_id") userId: Int
    ):Response<GetProfileResponse>

    @GET("/api/v1/courses")
    suspend fun getCourses(
        @Header("Authorization") token: String,
        @Query("teacher_id") teacherId: Int
    ):Response<GetCourseDataResponse>

    @GET("/api/v1/courses/{id}/sections")
    suspend fun getSections(
        @Header("Authorization") token: String,
        @Path("id") id: Int,
        @Query("teacher_id") teacherId: Int
    ):Response<GetSectionResponse>

    @GET("/api/v1/sections/{id}/files")
    suspend fun getFiles(
        @Header("Authorization") token: String,
        @Path("id") id: Int,
        @Query("teacher_id") teacherId: Int
    ):Response<GetFileResponse>

    @GET("/api/v1/sections/{id}/videos")
    suspend fun getVideos(
        @Header("Authorization") token: String,
        @Path("id") id: Int,
        @Query("teacher_id") teacherId: Int
    ):Response<GetVideoResponse>

}
