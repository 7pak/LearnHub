package com.example.courseapp.data.remote.teacher_post_dto

import android.net.Uri

data class AddFileFields(
    val fileId:Int?=null,
    val fileUri: Uri?,
    val sectionId:Int
)
