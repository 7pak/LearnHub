package com.example.courseapp.data.remote

import com.example.courseapp.data.remote.teacher_post_dto.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.*

interface CourseApi {

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



    @POST("/api/v1/sections")
    suspend fun addSection(
        @Header("Authorization") token: String,
        @Body addSectionFields: AddSectionFields
    ): Response<AddSectionResponse>

    @PUT("/api/v1/sections/{id}")
    suspend fun updateSection(
        @Header("Authorization") token: String,
        @Path("id") id: Int, // Replace Int with the appropriate data type for your section ID
        @Body addSectionFields: AddSectionFields // Define this data class as needed
    ): Response<StatusResponse>

    @DELETE("/api/v1/sections/{id}")
    suspend fun deleteSection(
        @Header("Authorization") token: String,
        @Path("id") id: Int,
    ): Response<StatusResponse>


    @Multipart
    @POST("/api/v1/files")
    suspend fun addFile(
        @Header("Authorization") token: String,
        @Part fileUrl: MultipartBody.Part?,
        @Part("section_id") section_id: Int
        ): Response<AddFileResponse>

    @DELETE("/api/v1/files/{id}")
    suspend fun deleteFile(
        @Header("Authorization") token: String,
        @Path("id") id: Int,
    ): Response<StatusResponse>


    @Multipart
    @POST("/api/v1/videos")
    suspend fun addVideo(
        @Header("Authorization") token: String,
        @Part videoUrl: MultipartBody.Part?,
        @Part("title") title: RequestBody,
        @Part("visible") visible: Int,
        @Part("section_id") section_id: Int
    ): Response<AddVideoResponse>

    @DELETE("/api/v1/videos/{id}")
    suspend fun deleteVideo(
        @Header("Authorization") token: String,
        @Path("id") id: Int,
    ): Response<StatusResponse>


    /////////////////////////////////////////////////////////////////////////////////////////////

    @GET("/api/v1/teacher/profile")
    suspend fun getProfileInfo(
        @Header("Authorization") token: String,
        @Query("teacher_id") teacherId: Int = 2// Assuming teacher_id is an integer
    ):Response<GetProfileResponse>

    @GET("/api/v1/courses")
    suspend fun getCourses(
        @Header("Authorization") token: String,
        @Query("teacher_id") teacherId: Int = 2// Assuming teacher_id is an integer
    ):Response<GetCourseDataResponse>

    @GET("/api/v1/teacher/{id}/sections")
    suspend fun getSections(
        @Header("Authorization") token: String,
        @Path("id") id: Int
    ):Response<GetSectionResponse>

    @GET("/api/v1/teacher/{sectionId}/videos")
    suspend fun getVideos(
        @Header("Authorization") token: String,
        @Path("sectionId") sectionId: Int
    ):Response<GetVideoResponse>

    @GET("/api/v1/teacher/{sectionId}/files")
    suspend fun getFile(
        @Header("Authorization") token: String,
        @Path("sectionId") sectionId: Int
    ):Response<GetFileResponse>

}
