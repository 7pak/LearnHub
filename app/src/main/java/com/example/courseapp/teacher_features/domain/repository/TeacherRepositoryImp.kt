package com.example.courseapp.teacher_features.domain.repository

import android.util.Log
import com.example.courseapp.common.StatusResponse
import com.example.courseapp.teacher_features.data.remote.AddSectionResponse
import com.example.courseapp.teacher_features.data.remote.GetCourseDataResponse
import com.example.courseapp.teacher_features.data.remote.GetFileResponse
import com.example.courseapp.teacher_features.data.remote.GetProfileResponse
import com.example.courseapp.teacher_features.data.remote.GetSectionResponse
import com.example.courseapp.teacher_features.data.remote.GetVideoResponse
import com.example.courseapp.teacher_features.data.remote.TeacherApi
import com.example.courseapp.teacher_features.data.remote.UpdatePartsApi
import com.example.courseapp.teacher_features.data.remote.teacher_post_dto.AddSectionFields
import com.example.courseapp.teacher_features.data.repository.TeacherRepository
import io.ktor.client.request.forms.MultiPartFormDataContent
import io.ktor.client.statement.HttpResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Response
import javax.inject.Inject


class TeacherRepositoryImp @Inject constructor(
    private val teacherApi: TeacherApi,
    private val tokenFlow: Flow<String?>,
    private val updatePartsApi: UpdatePartsApi
) : TeacherRepository {

    override suspend fun addCourse(
        category_id: Int,
        description: String,
        price: String,
        title: String,
        photoPart: MultipartBody.Part?
    ): Response<com.example.courseapp.teacher_features.data.remote.AddCourseResponse> {
        val token = tokenFlow.firstOrNull()
        Log.d(
            "AddCourse",
            "updateFile: $category_id desciption: $description  title: $title  ,,,,,, $photoPart"
        )

        return teacherApi.addCourse(
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



        return updatePartsApi.updateCourse(
            "Bearer $token",
            id = id,
            map = multipart
        )
    }

    override suspend fun deleteCourse(id: Int): Response<StatusResponse> {
        val token = tokenFlow.firstOrNull()
        return teacherApi.deleteCourse(
            "Bearer $token",
            id = id
        )
    }

    override suspend fun addSection(
        addSectionFields: AddSectionFields
    ): Response<AddSectionResponse> {
        val token = tokenFlow.firstOrNull()

        return teacherApi.addSection(
            "Bearer $token",
            addSectionFields = addSectionFields
        )
    }

    override suspend fun updateSection(
        id: Int,
        addSectionFields: AddSectionFields
    ): Response<StatusResponse> {
        val token = tokenFlow.firstOrNull()

        return teacherApi.updateSection(
            "Bearer $token",
            id = id,
            addSectionFields = addSectionFields
        )
    }

    override suspend fun deleteSection(id: Int): Response<StatusResponse> {
        val token = tokenFlow.firstOrNull()

        return teacherApi.deleteSection(
            "Bearer $token",
            id = id
        )
    }

    override suspend fun addFile(
        sectionId: Int,
        filePart: MultipartBody.Part?
    ): Response<com.example.courseapp.teacher_features.data.remote.AddFileResponse> {
        val token = tokenFlow.firstOrNull()
        return teacherApi.addFile(
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

        return updatePartsApi.updateFile(
            "Bearer $token",
            id = id,
            map = multipart
        )
    }

    override suspend fun deleteFile(id: Int): Response<StatusResponse> {
        val token = tokenFlow.firstOrNull()

        return teacherApi.deleteFile(
            "Bearer $token",
            id = id
        )
    }


    override suspend fun addVideo(
        sectionId: Int,
        title: String,
        visible: Int,
        videoUrl: MultipartBody.Part?
    ): Response<com.example.courseapp.teacher_features.data.remote.AddVideoResponse> {
        val token = tokenFlow.firstOrNull()


        return teacherApi.addVideo(
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


        return updatePartsApi.updateVideo(
            "Bearer $token",
            id = id,
            map = multipart
        )
    }

    override suspend fun deleteVideo(id: Int): Response<StatusResponse> {
        val token = tokenFlow.firstOrNull()


        return teacherApi.deleteVideo(
            "Bearer $token",
            id = id
        )
    }

    ///////////////////////////////////////////////////////////////////////

    override suspend fun getProfileInfo(userId:Int): Response<GetProfileResponse> {
        val token = tokenFlow.firstOrNull()

        return teacherApi.getProfileInfo(
            "Bearer $token",
            userId = userId
        )
    }

    override suspend fun deleteAccount(): Response<StatusResponse> {
        val token = tokenFlow.firstOrNull()

        return teacherApi.deleteAccount(
            "Bearer $token"
        )
    }

    override suspend fun getCourses(teacherId:Int): Response<GetCourseDataResponse> {
        val token = tokenFlow.firstOrNull()
        Log.d("GetCourses", "getCourses: teacherId: $teacherId ")
        return teacherApi.getCourses(
            "Bearer $token",
            teacherId = teacherId
        )
    }

    override suspend fun getSections(id: Int,teacherId: Int): Response<GetSectionResponse> {
        val token = tokenFlow.firstOrNull()
        Log.d("AllSection", "getSection: teacherId: $teacherId ")

        return teacherApi.getSections(
            "Bearer $token",
            id = id,
            teacherId = teacherId
        )
    }

    override suspend fun getFiles(id: Int, teacherId: Int): Response<GetFileResponse> {
        val token = tokenFlow.firstOrNull()

        return teacherApi.getFiles(
            "Bearer $token",
            id = id,
            teacherId = teacherId
        )
    }

    override suspend fun getVideos(id: Int, teacherId: Int): Response<GetVideoResponse> {
        val token = tokenFlow.firstOrNull()

        return teacherApi.getVideos(
            "Bearer $token",
            id = id,
            teacherId = teacherId
        )
    }
}