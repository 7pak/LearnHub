package com.example.courseapp.teacher_features.data.remote.teacher_post_dto

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class AddCourseData(
    @SerializedName("course_id")
    val courseId: Int
)