package com.example.courseapp.domain.repository

import android.util.Log
import com.example.courseapp.data.remote.AddCourseResponse
import com.example.courseapp.data.remote.AddFileResponse
import com.example.courseapp.data.remote.AddSectionResponse
import com.example.courseapp.data.remote.AddVideoResponse
import com.example.courseapp.data.remote.CourseApi
import com.example.courseapp.data.remote.GetCourseDataResponse
import com.example.courseapp.data.remote.GetFileResponse
import com.example.courseapp.data.remote.GetProfileResponse
import com.example.courseapp.data.remote.GetSectionResponse
import com.example.courseapp.data.remote.GetVideoResponse
import com.example.courseapp.data.remote.StatusResponse
import com.example.courseapp.data.remote.UpdateApi
import com.example.courseapp.data.remote.teacher_post_dto.AddSectionFields
import com.example.courseapp.data.repository.TeacherRepository
import io.ktor.client.request.forms.MultiPartFormDataContent
import io.ktor.client.request.forms.formData
import io.ktor.client.statement.HttpResponse

import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Response
import java.io.File
import javax.inject.Inject


class TeacherRepositoryImp @Inject constructor(
    private val courseApi: CourseApi,
    private val tokenFlow: Flow<String?>,
    private val updateApi: UpdateApi
) : TeacherRepository {

    override suspend fun addCourse(
        category_id: Int,
        description: String,
        price: String,
        title: String,
        photoPart: MultipartBody.Part?
    ): Response<AddCourseResponse> {
        val token = tokenFlow.firstOrNull()
        Log.d(
            "AddCourse",
            "updateFile: $category_id desciption: $description  title: $title  ,,,,,, $photoPart"
        )

        return courseApi.addCourse(
            "Bearer $token",
            photo = photoPart,
            title = title.toRequestBody(),
            description = description.toRequestBody(),
            categoryId = category_id,
            price = price.toRequestBody()
        )

    }

    override suspend fun updateCourse(
        id: Int,
        multipart: MultiPartFormDataContent
    ): HttpResponse {
        val token = tokenFlow.firstOrNull()



        return updateApi.updateCourse(
            "Bearer $token",
            id = id,
            map = multipart
        )
    }

    override suspend fun deleteCourse(id: Int): Response<StatusResponse> {
        val token = tokenFlow.firstOrNull()
        return courseApi.deleteCourse(
            "Bearer $token",
            id = id
        )
    }

    override suspend fun addSection(
        addSectionFields: AddSectionFields
    ): Response<AddSectionResponse> {
        val token = tokenFlow.firstOrNull()

        return courseApi.addSection(
            "Bearer $token",
            addSectionFields = addSectionFields
        )
    }

    override suspend fun updateSection(
        id: Int,
        addSectionFields: AddSectionFields
    ): Response<StatusResponse> {
        val token = tokenFlow.firstOrNull()

        return courseApi.updateSection(
            "Bearer $token",
            id = id,
            addSectionFields = addSectionFields
        )
    }

    override suspend fun deleteSection(id: Int): Response<StatusResponse> {
        val token = tokenFlow.firstOrNull()

        return courseApi.deleteSection(
            "Bearer $token",
            id = id
        )
    }

    override suspend fun addFile(
        sectionId: Int,
        filePart: MultipartBody.Part?
    ): Response<AddFileResponse> {
        val token = tokenFlow.firstOrNull()
        return courseApi.addFile(
            "Bearer $token",
            fileUrl = filePart,
            section_id = sectionId
        )
    }

    override suspend fun updateFile(
        id: Int,
        multipart: MultiPartFormDataContent
    ): HttpResponse {
        val token = tokenFlow.firstOrNull()

        return updateApi.updateFile(
            "Bearer $token",
            id = id,
            map = multipart
        )
    }

    override suspend fun deleteFile(id: Int): Response<StatusResponse> {
        val token = tokenFlow.firstOrNull()

        return courseApi.deleteFile(
            "Bearer $token",
            id = id
        )
    }


    override suspend fun addVideo(
        sectionId: Int,
        title: String,
        visible: Int,
        videoUrl: MultipartBody.Part?
    ): Response<AddVideoResponse> {
        val token = tokenFlow.firstOrNull()


        return courseApi.addVideo(
            "Bearer $token",
            videoUrl = videoUrl,
            title = title.toRequestBody(),
            visible = visible,
            section_id = sectionId
        )
    }

    override suspend fun updateVideo(
        id: Int,
        multipart: MultiPartFormDataContent
    ): HttpResponse {
        val token = tokenFlow.firstOrNull()


        return updateApi.updateVideo(
            "Bearer $token",
            id = id,
            map = multipart
        )
    }

    override suspend fun deleteVideo(id: Int): Response<StatusResponse> {
        val token = tokenFlow.firstOrNull()


        return courseApi.deleteVideo(
            "Bearer $token",
            id = id
        )
    }

    ///////////////////////////////////////////////////////////////////////

    override suspend fun getProfileInfo(): Response<GetProfileResponse> {
        val token = tokenFlow.firstOrNull()

        return courseApi.getProfileInfo(
            "Bearer $token"
        )
    }

    override suspend fun deleteAccount(): Response<StatusResponse> {
        val token = tokenFlow.firstOrNull()

        return courseApi.deleteAccount(
            "Bearer $token"
        )
    }

    override suspend fun getCourses(): Response<GetCourseDataResponse> {
        val token = tokenFlow.firstOrNull()

        return courseApi.getCourses(
            "Bearer $token"
        )
    }

    override suspend fun getSections(id: Int): Response<GetSectionResponse> {
        val token = tokenFlow.firstOrNull()

        return courseApi.getSections(
            "Bearer $token",
            id = id
        )
    }

    override suspend fun getVideos(id: Int): Response<GetVideoResponse> {
        val token = tokenFlow.firstOrNull()

        return courseApi.getVideos(
            "Bearer $token",
            sectionId = id
        )
    }

    override suspend fun getFile(id: Int): Response<GetFileResponse> {
        val token = tokenFlow.firstOrNull()

        return courseApi.getFile(
            "Bearer $token",
            sectionId = id
        )
    }


}