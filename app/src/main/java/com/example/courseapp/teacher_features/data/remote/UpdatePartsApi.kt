package com.example.courseapp.teacher_features.data.remote

import de.jensklingenberg.ktorfit.http.Body
import de.jensklingenberg.ktorfit.http.Header
import de.jensklingenberg.ktorfit.http.POST
import de.jensklingenberg.ktorfit.http.Path
import io.ktor.client.request.forms.MultiPartFormDataContent
import io.ktor.client.statement.HttpResponse

interface UpdatePartsApi {

    /**
     * we used ktor fit  to update multipart data since i couldn't do that with retrofit
     */
    @POST("api/v1/courses/{id}")
    suspend fun updateCourse(
        @Header("Authorization") token: String,
        @Path("id") id:Int,
        @Body map: MultiPartFormDataContent
    ): HttpResponse


    @POST("api/v1/sections/files/{id}")
    suspend fun updateFile(
        @Header("Authorization") token: String,
        @Path("id") id:Int,
        @Body map: MultiPartFormDataContent
    ):HttpResponse


    @POST("api/v1/sections/videos/{id}")
    suspend fun updateVideo(
        @Header("Authorization") token: String,
        @Path("id") id:Int,
        @Body map: MultiPartFormDataContent
    ):HttpResponse


}