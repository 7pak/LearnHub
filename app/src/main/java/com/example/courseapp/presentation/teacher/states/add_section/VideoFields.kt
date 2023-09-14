package com.example.courseapp.presentation.teacher.states.add_section

import android.net.Uri

data class VideoFields(
    val sectionId:Int,
    val videoId:Int?=null,
    val videoName:String,
    val video: Uri?,
    val isVisible:Boolean,
    val isAddingNew:Boolean?=null
)
