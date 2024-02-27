package com.example.courseapp.teacher_features.presentation.states.add_section

import android.net.Uri

data class FileFields(
    val sectionId:Int,
    val fileUri: Uri,
    val fileName:String
)
