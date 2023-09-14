package com.example.courseapp.presentation.teacher.states.add_section

import android.net.Uri

data class FileFields(
    val sectionId:Int,
    val fileUri: Uri,
    val fileName:String
)
