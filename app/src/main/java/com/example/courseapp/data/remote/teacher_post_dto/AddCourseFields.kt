package com.example.courseapp.data.remote.teacher_post_dto

import android.net.Uri

data class AddCourseFields(
    val courseId:Int?=null,
    val category_id: Int,
    val description: String,
    val photo: Uri?,
    val price: String,
    val title: String,
)