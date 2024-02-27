package com.example.courseapp.navigation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.courseapp.teacher_features.data.remote.teacher_get_dto.SectionData
import com.example.courseapp.teacher_features.data.remote.teacher_post_dto.AddCourseFields
import com.example.courseapp.teacher_features.presentation.states.add_section.VideoFields

class SharedViewModel:ViewModel() {

    var courseFields: AddCourseFields? by mutableStateOf(null)
        private set

    var videoFields: VideoFields? by mutableStateOf(null)
        private set

    var sectionData:SectionData? by mutableStateOf(null)
    fun updateCourseFields(newCourseFields: AddCourseFields?){
        courseFields = newCourseFields

    }
    fun updateVideoFields(newVideoFields: VideoFields?){
            videoFields = newVideoFields

    }
    fun updateSectionData(newSectionData: SectionData?){
        sectionData = newSectionData

    }
}