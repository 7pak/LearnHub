package com.example.courseapp.data.remote.teacher_post_dto

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class AddCourseData(
    @SerializedName("Course_id")
    val courseId: Int
)