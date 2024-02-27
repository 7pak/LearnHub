package com.example.courseapp.teacher_features.presentation.states

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.courseapp.navigation.POST_COURSE_ID
import com.example.courseapp.navigation.POST_SECTION_ID
import com.example.courseapp.teacher_features.presentation.states.add_courses.CourseState
import com.example.courseapp.teacher_features.presentation.states.add_section.SectionState
import com.example.courseapp.teacher_features.presentation.states.add_section.VideoFields
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class CourseStatesModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    var courseState = mutableStateOf(CourseState())
        private set

    fun updateCourseState(newState: CourseState) {
        courseState.value = newState
    }

///////////////////////////////////////////////////////////////

    var currentCourseId by mutableStateOf(-1)
    var currentSectionId by mutableStateOf(-1)

    init {

        val courseIdString = savedStateHandle.get<String>(POST_COURSE_ID)
        val sectionIdString = savedStateHandle.get<String>(POST_SECTION_ID)

        val courseId = courseIdString?.toIntOrNull() ?: -1
        val sectionId = sectionIdString?.toIntOrNull() ?: -1


        currentCourseId = courseId

        currentSectionId = sectionId
    }

    private var sectionState = mutableStateOf(SectionState())


    fun updateSectionState(newState: SectionState) {
        sectionState.value = newState
    }

    private val _sections = MutableStateFlow<List<SectionState>>(emptyList())
    val sections: StateFlow<List<SectionState>> = _sections

    fun addToSectionsList() {
        val updatedList = _sections.value.toMutableList()
        val lastSection: SectionState? =
            updatedList.find { it.sectionId == sectionState.value.sectionId }
        if (lastSection != null) {
            return
        }
        updatedList.add(sectionState.value)
        _sections.value = updatedList
        sectionState.value = SectionState()
    }

    fun updateSectionList(section: SectionState) {
        val updatedList = _sections.value.toMutableList()
        val index = updatedList.indexOfLast { it.sectionId == section.sectionId }

        if (index != -1) {
            updatedList[index] = section
            _sections.value = updatedList
        }
        sectionState.value = SectionState()
    }

    fun deleteSection() {
        val currentList = _sections.value.toMutableList()
        val sectionToDelete = sectionState.value

        currentList.remove(sectionToDelete)
        _sections.value = currentList
        sectionState.value = SectionState()
    }


    // Function to add a video to a specific section based on sectionId
    fun addVideoToSection(sectionId: Int, video: VideoFields) {
        val updatedList = _sections.value.toMutableList()

        val currentSection = updatedList.find { it.sectionId == sectionId }

        val sectionIndex = updatedList.indexOf(currentSection)

        if (sectionIndex != -1) {
            val section = updatedList[sectionIndex]
            val updatedSection = section.copy(sectionVideos = section.sectionVideos + video)
            updatedList[sectionIndex] = updatedSection
            Log.d("AddCourse", "list of video:${updatedSection.sectionVideos.size}")
            _sections.value = updatedList
        }
    }


}