package com.example.courseapp.teacher_features.data.remote.teacher_get_dto

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable


@Serializable
data class SectionData(
    @SerializedName("course_id")
    val courseId: Int,
    @SerializedName("section_id")
    val sectionId: Int,
    @SerializedName("section_title")
    val sectionTitle: String,
)