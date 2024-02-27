package com.example.courseapp.teacher_features.data.remote.teacher_post_dto

import android.net.Uri

data class AddVideoFields(
    val videoId:Int?=null,
    val section_id: Int,
    val title: String,
    val videoUrl: Uri?,
    val visible: Boolean
)