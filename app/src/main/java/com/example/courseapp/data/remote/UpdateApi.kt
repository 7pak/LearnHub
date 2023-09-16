package com.example.courseapp.data.remote

import de.jensklingenberg.ktorfit.http.Body
import de.jensklingenberg.ktorfit.http.Header
import de.jensklingenberg.ktorfit.http.Headers
import de.jensklingenberg.ktorfit.http.POST
import de.jensklingenberg.ktorfit.http.PUT
import de.jensklingenberg.ktorfit.http.Path
import io.ktor.client.request.forms.MultiPartFormDataContent
import io.ktor.client.statement.HttpResponse

interface UpdateApi {


    @POST("api/v1/courses/{id}")
    suspend fun updateCourse(
        @Header("Authorization") token: String,
        @Path("id") id:Int,
        @Body map: MultiPartFormDataContent
    ): HttpResponse


    @POST("api/v1/files/{id}")
    suspend fun updateFile(
        @Header("Authorization") token: String,
        @Path("id") id:Int,
        @Body map: MultiPartFormDataContent
    ):HttpResponse


    @POST("api/v1/videos/{id}")
    suspend fun updateVideo(
        @Header("Authorization") token: String,
        @Path("id") id:Int,
        @Body map: MultiPartFormDataContent
    ):HttpResponse


}