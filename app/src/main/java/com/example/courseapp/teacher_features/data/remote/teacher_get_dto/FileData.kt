package com.example.courseapp.teacher_features.data.remote.teacher_get_dto

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class FileData(
    @SerializedName("file_url")
    val fileUrl: String,
    @SerializedName("file_id")
    val fileId:Int
)