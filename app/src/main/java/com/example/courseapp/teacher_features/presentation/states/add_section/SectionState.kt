package com.example.courseapp.teacher_features.presentation.states.add_section

data class SectionState(
    val sectionId:Int?=null,
    val sectionName:String = "",
    val sectionVideos:List<VideoFields> = emptyList(),
    val sectionFile: FileFields?=null,
    val showDialog:Boolean = false
)
