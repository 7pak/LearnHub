package com.example.courseapp.presentation.teacher

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
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
import com.example.courseapp.data.remote.teacher_post_dto.AddVideoFields
import com.example.courseapp.domain.model.TeacherModel
import com.example.courseapp.navigation.SharedViewModel
import com.example.courseapp.presentation.teacher.common_item.SectionVideos
import com.example.courseapp.presentation.teacher.common_item.addVideo
import com.example.courseapp.presentation.teacher.common_item.updateVideo
import com.example.courseapp.presentation.teacher.states.CourseStatesModel
import com.example.courseapp.presentation.teacher.states.add_section.VideoFields
import com.example.courseapp.presentation.ui.theme.CourseAppTheme

@Composable
fun AddVideoScreen(
    navController: NavHostController,
    courseStatesModel: CourseStatesModel = hiltViewModel(),
    teacherModel: TeacherModel = hiltViewModel(),
    sharedViewModel: SharedViewModel
) {

    val lifecycleOwner = LocalLifecycleOwner.current
    var isLoading by remember {
        mutableStateOf(false)
    }

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.background),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                SectionVideos(
                    sectionId = courseStatesModel.currentSectionId,
                    sharedViewModel = sharedViewModel
                ) { videoFields, isUpdating ->
                    isLoading = true
                    Log.d("AddCourse", "AddVideoScreen:$isUpdating ")
                    Log.d("AddCourse", "AddVideoScreen:${sharedViewModel.videoFields} ")
                    if (isUpdating) {
                        updateVideo(
                            lifecycleOwner = lifecycleOwner,
                            teacherModel = teacherModel,
                            addVideoFields = AddVideoFields(
                                videoId = sharedViewModel.videoFields?.videoId,
                                section_id = sharedViewModel.videoFields?.sectionId!!,
                                title = sharedViewModel.videoFields?.videoName!!,
                                videoUrl = sharedViewModel.videoFields?.video,
                                visible = sharedViewModel.videoFields?.isVisible!!
                            ),onFailed = {isLoading = false}
                        ) {
                            isLoading = false
                            teacherModel.fetchAllVideos(sharedViewModel.videoFields!!.sectionId)
                            sharedViewModel.updateVideoFields(null)
                            navController.popBackStack()
                        }
                    } else {
                        addVideo(
                            teacherModel = teacherModel,
                            lifecycleOwner = lifecycleOwner,
                            addVideoFields = AddVideoFields(
                                section_id = videoFields.sectionId,
                                title = videoFields.videoName,
                                visible = videoFields.isVisible,
                                videoUrl = videoFields.video
                            ),onFailed = {isLoading = false}
                        ) {
                            isLoading = false
                            sharedViewModel.updateVideoFields(videoFields)
                            navController.popBackStack()
                        }
                    }
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
fun AddVideoScreenPreview() {
    CourseAppTheme {
        //AddVideoScreen()
    }
}