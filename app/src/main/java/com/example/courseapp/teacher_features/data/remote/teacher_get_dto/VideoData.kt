package com.example.courseapp.teacher_features.data.remote.teacher_get_dto

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable


@Serializable
data class VideoData(
    @SerializedName("video_title")
    val videoTitle: String,
    @SerializedName("video_ur")
    val videoUrl: String,
    @SerializedName("video_visible")
    val videoVisible: Int,
    @SerializedName("video_id")
    val videoId:Int
)