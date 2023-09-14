package com.example.courseapp.presentation.teacher

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.courseapp.data.remote.teacher_post_dto.AddFileFields
import com.example.courseapp.data.remote.teacher_post_dto.AddSectionFields
import com.example.courseapp.domain.model.TeacherModel
import com.example.courseapp.navigation.SharedViewModel
import com.example.courseapp.presentation.teacher.common_item.Sections
import com.example.courseapp.presentation.teacher.common_item.addFile
import com.example.courseapp.presentation.teacher.common_item.addSection
import com.example.courseapp.presentation.teacher.common_item.deleteSection
import com.example.courseapp.presentation.teacher.common_item.updateSection
import com.example.courseapp.presentation.teacher.states.CourseStatesModel
import com.example.courseapp.presentation.teacher.states.add_section.SectionState
import com.example.courseapp.presentation.ui.theme.CourseAppTheme

@Composable
fun AddSectionScreen(
    navController: NavHostController,
    teacherModel: TeacherModel = hiltViewModel(),
    courseStatesModel: CourseStatesModel = hiltViewModel(),
    sharedViewModel: SharedViewModel
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    var preSectionId: Int? = null

    var isLoading by remember {
        mutableStateOf(false)
    }

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.background)
        ) {
            item {
                Sections(
                    courseStatesModel = courseStatesModel,
                    navController = navController,
                    onUpdateSection = {
                         isLoading = true
                        updateSection(
                            lifecycleOwner = lifecycleOwner,
                            teacherModel = teacherModel,
                            sectionId = it.sectionId!!,
                            title = it.sectionName,
                            onFailed = { isLoading = false }
                        ) {
                            isLoading = false
                            courseStatesModel.updateSectionList(it)
                        }
                    }, onDeleteSection = {
                        it.sectionId?.let { id ->
                             isLoading = true
                            deleteSection(
                                lifecycleOwner = lifecycleOwner,
                                teacherModel = teacherModel,
                                sectionId = id,
                                onFailed = { isLoading = false }
                            ) {
                                 isLoading = false
                                courseStatesModel.updateSectionState(it)
                                courseStatesModel.deleteSection()
                            }
                            courseStatesModel.updateSectionState(SectionState())
                        }

                    },
                    onCreateFile = {
                        it.sectionFile?.let { fileFields ->
                           isLoading = true
                            addFile(
                                lifecycleOwner = lifecycleOwner,
                                teacherModel = teacherModel,
                                addFileFields = AddFileFields(
                                    fileUri = fileFields.fileUri,
                                    sectionId = fileFields.sectionId,
                                ),onFailed = { isLoading = false }
                            ) {
                                 isLoading = false
                                courseStatesModel.updateSectionList(it)
                            }
                        }

                    }
                ) {
                    isLoading = true
                    addSection(
                        teacherModel = teacherModel,
                        lifecycleOwner = lifecycleOwner,
                        addSectionFields = AddSectionFields(
                            course_id = courseStatesModel.currentCourseId,
                            title = it.sectionName
                        ),onFailed = { isLoading = false }
                    ) { sectionId ->
                        if (sectionId != preSectionId) {
                            isLoading = false
                            courseStatesModel.updateSectionState(
                                it.copy(
                                    sectionId = sectionId,
                                    sectionName = it.sectionName
                                )
                            )
                            courseStatesModel.addToSectionsList()
                            courseStatesModel.updateSectionState(SectionState())
                        }
                        preSectionId = sectionId
                    }
                }

                //used shared viewModel to share arguments between screens
                LaunchedEffect(key1 = true) {
                    sharedViewModel.videoFields?.let {
                        courseStatesModel.addVideoToSection(
                            sectionId = it.sectionId,
                            video = it
                        )
                        sharedViewModel.updateVideoFields(null)
                    }
                    courseStatesModel.updateSectionState(SectionState())
                }
            }
        }
        if (isLoading) {
            CircularProgressIndicator(color = MaterialTheme.colors.primaryVariant)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AddCourseSectionPreview() {
    CourseAppTheme {
        //AddSectionScreen()
    }
}