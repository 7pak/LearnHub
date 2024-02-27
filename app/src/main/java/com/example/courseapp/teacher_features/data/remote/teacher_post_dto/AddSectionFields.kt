package com.example.courseapp.teacher_features.data.remote.teacher_post_dto

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class AddSectionFields(
    val course_id: Int? = null,
    val title: String,
    @SerializedName("section_id")
    val sectionId:Int?=null
)