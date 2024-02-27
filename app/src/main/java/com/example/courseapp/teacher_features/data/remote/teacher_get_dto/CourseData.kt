package com.example.courseapp.teacher_features.data.remote.teacher_get_dto

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class CourseData(
    @SerializedName("course_description")
    val courseDescription: String,
    @SerializedName("course_name")
    val courseName: String,
    @SerializedName("course_photo")
    val coursePhoto: String,
    @SerializedName("course_price")
    val coursePrice:Int,
    @SerializedName("course_id")
    val courseId:Int
)