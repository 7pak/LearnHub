package com.example.courseapp.presentation.teacher.states.add_section

data class SectionState(
    val sectionId:Int?=null,
    val sectionName:String = "",
    val sectionVideos:List<VideoFields> = emptyList(),
    val sectionFile:FileFields?=null,
    val showDialog:Boolean = false
)
