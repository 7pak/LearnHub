package com.example.courseapp.navigation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.courseapp.data.remote.teacher_post_dto.AddCourseFields
import com.example.courseapp.presentation.teacher.states.add_section.VideoFields

class SharedViewModel:ViewModel() {

    var courseFields:AddCourseFields? by mutableStateOf(null)
        private set

    var videoFields:VideoFields? by mutableStateOf(null)
        private set

    fun updateCourseFields(newVideoFields: AddCourseFields?){
        courseFields = newVideoFields

    }
    fun updateVideoFields(newVideoFields: VideoFields?){
            videoFields = newVideoFields

    }
}