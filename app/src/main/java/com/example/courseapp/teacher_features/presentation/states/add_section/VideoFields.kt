package com.example.courseapp.teacher_features.presentation.states.add_section

import android.net.Uri

data class VideoFields(
    val sectionId:Int,
    val videoId:Int?=null,
    val videoName:String,
    val video: Uri?,
    val isVisible:Boolean,
    val isAddingNew:Boolean?=null
)
