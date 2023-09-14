package com.example.courseapp.data.remote

import com.example.courseapp.data.remote.teacher_post_dto.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.*

interface CourseApi {

    @POST("/api/register")
    suspend fun registerUser(
        @Body registerUserData: RegisterUserData
    ): Response<RegisterResponse>


    @POST("/api/login")
    suspend fun loginUser(
        @Body loginUserData: LoginUserData
    ): Response<LoginResponse>

    @POST("/api/verify-otp")
    suspend fun verifyUser(
        @Header("Authorization") token: String,
        @Body verificationData: VerificationData
    ): Response<VerificationResponse>

    @POST("/api/resend-otp")
    suspend fun resendOtp(@Header("Authorization") token: String): Response<StatusResponse>


    @POST("/api/password/verification")
    suspend fun accountVerification(@Body fieldsVerificationData: FieldsVerificationData): Response<EmailVerificationResponse>

    @POST("api/password/reset")
    suspend fun resetPassword(
        @Header("Authorization") token: String,
        @Body fieldsVerificationData: FieldsVerificationData
    ): Response<StatusResponse>


    @POST("/api/teacher/logout")
    suspend fun logoutUser(@Header("Authorization") token: String): Response<StatusResponse>




//    @PUT("/api/teacher/{id}")
//    suspend fun deleteAccount(
//        @Header("Authorization") token: String,
//    ):Response<StatusResponse>

    @DELETE("/api/teacher/destroy")
    suspend fun deleteAccount(
        @Header("Authorization") token: String,
    ):Response<StatusResponse>

    @Multipart
    @POST("/api/courses")
    suspend fun addCourse(
        @Header("Authorization") token: String,
        @Part photo: MultipartBody.Part?,
        @Part("title") title: RequestBody,
        @Part("description") description: RequestBody,
        @Part("category_id") categoryId: Int,
        @Part("price") price: RequestBody
    ): Response<AddCourseResponse>

    @PUT("/api/courses/{id}")
    @Multipart
    suspend fun updateCourse(
        @Header("Authorization") token: String,
        @Path("id") id: Int,
        @Part photo: MultipartBody.Part?,
        @Part("title") title: RequestBody,
        @Part("description") description: RequestBody,
        @Part("category_id") categoryId: Int,
        @Part("price") price: RequestBody,
    ): Response<StatusResponse>

    @DELETE("/api/courses/{id}")
    suspend fun deleteCourse(
        @Header("Authorization") token: String,
        @Path("id") id: Int,
    ): Response<StatusResponse>



    @POST("/api/sections")
    suspend fun addSection(
        @Header("Authorization") token: String,
        @Body addSectionFields: AddSectionFields
    ): Response<AddSectionResponse>

    @PUT("/api/sections/{id}")
    suspend fun updateSection(
        @Header("Authorization") token: String,
        @Path("id") id: Int, // Replace Int with the appropriate data type for your section ID
        @Body addSectionFields: AddSectionFields // Define this data class as needed
    ): Response<StatusResponse>

    @DELETE("/api/sections/{id}")
    suspend fun deleteSection(
        @Header("Authorization") token: String,
        @Path("id") id: Int,
    ): Response<StatusResponse>


    @Multipart
    @POST("/api/files")
    suspend fun addFile(
        @Header("Authorization") token: String,
        @Part fileUrl: MultipartBody.Part?,
        @Part("section_id") section_id: Int
        ): Response<AddFileResponse>

    @Multipart
    @PUT("/api/files/{id}")
    suspend fun updateFile(
        @Header("Authorization") token: String,
        @Path("id") id: Int,
        @Part("section_id")sectionId: Int,
        @Part fileUrl: MultipartBody.Part?,
    ): Response<StatusResponse>

    @DELETE("/api/files/{id}")
    suspend fun deleteFile(
        @Header("Authorization") token: String,
        @Path("id") id: Int,
    ): Response<StatusResponse>


    @Multipart
    @POST("/api/videos")
    suspend fun addVideo(
        @Header("Authorization") token: String,
        @Part videoUrl: MultipartBody.Part?,
        @Part("title") title: RequestBody,
        @Part("visible") visible: Int,
        @Part("section_id") section_id: Int
    ): Response<AddVideoResponse>

    @Multipart
    @PUT("/api/videos/{id}")
    suspend fun updateVideo(
        @Header("Authorization") token: String,
        @Path("id") id: Int,
        @Part("section_id")sectionId: Int,
        @Part videoUrl: MultipartBody.Part?,
        @Part("title") title: RequestBody,
        @Part("visible") visible: Int,
    ): Response<StatusResponse>

    @DELETE("/api/videos/{id}")
    suspend fun deleteVideo(
        @Header("Authorization") token: String,
        @Path("id") id: Int,
    ): Response<StatusResponse>


    /////////////////////////////////////////////////////////////////////////////////////////////

    @GET("/api/teacher/profile")
    suspend fun getProfileInfo(
        @Header("Authorization") token: String,
    ):Response<GetProfileResponse>

    @GET("/api/teacher/courses")
    suspend fun getCourses(
        @Header("Authorization") token: String,
    ):Response<GetCourseDataResponse>

    @GET("/api/teacher/{id}/sections")
    suspend fun getSections(
        @Header("Authorization") token: String,
        @Path("id") id: Int
    ):Response<GetSectionResponse>

    @GET("/api/teacher/{sectionId}/videos")
    suspend fun getVideos(
        @Header("Authorization") token: String,
        @Path("sectionId") sectionId: Int
    ):Response<GetVideoResponse>

    @GET("/api/teacher/{sectionId}/files")
    suspend fun getFile(
        @Header("Authorization") token: String,
        @Path("sectionId") sectionId: Int
    ):Response<GetFileResponse>

}
