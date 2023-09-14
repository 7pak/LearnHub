package com.example.courseapp.data.remote

import com.example.courseapp.common.Constants.BASE_URL
import de.jensklingenberg.ktorfit.Ktorfit
import de.jensklingenberg.ktorfit.http.Body
import de.jensklingenberg.ktorfit.http.Header
import de.jensklingenberg.ktorfit.http.PUT
import de.jensklingenberg.ktorfit.http.Path
import io.ktor.client.request.forms.MultiPartFormDataContent

interface UpdateApi {

//    suspend fun putUpdateCourse(
//        token:String,
//        id: Int,
//        description: String,
//        price: String,
//        title: String,
//        category_id: Int,
//        photoPart: MultipartBody.Part?
//    ): StatusResponse?

    @PUT("api/courses/{id}")
    suspend fun updateCourse(
        @Header("Authorization") token: String,
        @Path("id") id:Int,
        @Body map:MultiPartFormDataContent
    ):String?


}