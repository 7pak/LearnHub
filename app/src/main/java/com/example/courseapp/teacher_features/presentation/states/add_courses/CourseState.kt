package com.example.courseapp.teacher_features.presentation.states.add_courses

import android.net.Uri

data class CourseState(
    val title:String = "",
    val description:String= "",
    val categoryId:Int?= null,
    val price:String= "",
    val photoUri: Uri? = null
)
