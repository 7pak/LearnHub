package com.example.courseapp.data.remote.teacher_get_dto

import kotlinx.serialization.Serializable

@Serializable
data class CourseData(
    val courseDescription: String,
    val courseName: String,
    val coursePhoto: String,
    val courseId:Int
)