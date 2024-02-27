package com.example.courseapp.teacher_features.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.example.courseapp.navigation.Screens
import com.example.courseapp.navigation.SharedViewModel
import com.example.courseapp.teacher_features.data.remote.teacher_get_dto.SectionData
import com.example.courseapp.teacher_features.data.remote.teacher_post_dto.AddSectionFields
import com.example.courseapp.teacher_features.domain.model.TeacherModel
import com.example.courseapp.teacher_features.presentation.common_item.AddSection
import com.example.courseapp.teacher_features.presentation.common_item.addSection
import com.example.courseapp.teacher_features.presentation.common_item.deleteSection
import com.example.courseapp.teacher_features.presentation.common_item.swipeActionItem
import com.example.courseapp.teacher_features.presentation.common_item.updateSection
import kotlinx.coroutines.delay
import me.saket.swipe.SwipeableActionsBox

@Composable
fun SectionsScreen(
    navController: NavHostController,
    teacherModel: TeacherModel = hiltViewModel(),
    sharedViewModel: SharedViewModel
) {

    var isLoading by remember {
        mutableStateOf(true)
    }


    LaunchedEffect(key1 = teacherModel) {
        delay(1000)
        teacherModel.fetchAllSections(teacherModel.currentCourseId)
        isLoading = false
    }

    val lifecycleOwner = LocalLifecycleOwner.current


    val sections by teacherModel.sections.collectAsStateWithLifecycle(initialValue = emptyList())
    if (!sections.isNullOrEmpty()) isLoading = false


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background), contentAlignment = Alignment.Center
    ) {

        LazyColumn(modifier = Modifier.fillMaxSize()) {

            items(sections) { section ->
                SectionItem(
                    section = section,
                    onUpdateSection = {
                        isLoading = true
                        updateSection(
                            lifecycleOwner,
                            teacherModel,
                            sectionId = section.sectionId,
                            title = it.sectionTitle, onFailed = { isLoading = false }
                        ) {
                            isLoading = false
                            teacherModel.fetchAllSections(teacherModel.currentCourseId)
                        }
                    },
                    onDeleteSection = {
                        isLoading = true
                        deleteSection(
                            lifecycleOwner,
                            teacherModel,
                            sectionId = section.sectionId,
                            onFailed = { isLoading = false }
                        ) {
                            isLoading = false
                            teacherModel.fetchAllSections(teacherModel.currentCourseId)
                        }
                    }
                ) {
                    sharedViewModel.updateSectionData(null)
                    sharedViewModel.updateSectionData(it)
                    navController.navigate(Screens.DocumentVideosScreen.passSectionId(section.sectionId))
                }

            }
            item {
                AddSection {
                    isLoading = true
                    addSection(
                        teacherModel, AddSectionFields(
                            course_id = teacherModel.currentCourseId,
                            title = it,
                        ), lifecycleOwner = lifecycleOwner, onFailed = { isLoading = false }
                    ) {
                        isLoading = false
                        teacherModel.fetchAllSections(teacherModel.currentCourseId)
                    }
                }
            }
        }

        if (isLoading) {
            CircularProgressIndicator(color = MaterialTheme.colors.primaryVariant)
        }

    }
}


@Composable
fun SectionItem(
    section: SectionData,
    onUpdateSection: (SectionData) -> Unit,
    onDeleteSection: (SectionData) -> Unit,
    onSectionClicked: (SectionData) -> Unit
) {

    var isAddingSection by remember {
        mutableStateOf(false)
    }
    var sectionName by remember {
        mutableStateOf("")
    }

    if (isAddingSection) {
        TextField(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 20.dp, horizontal = 10.dp)
                .background(Color.Transparent),
            value = sectionName,
            colors = TextFieldDefaults.textFieldColors(
                textColor = MaterialTheme.colors.onBackground,
                cursorColor = MaterialTheme.colors.primaryVariant,
                backgroundColor = MaterialTheme.colors.background
            ),
            textStyle = TextStyle(fontSize = MaterialTheme.typography.h6.fontSize),
            onValueChange = {
                sectionName = it
            },
            placeholder = {
                Text(
                    text = "New section name",
                    style = MaterialTheme.typography.h6.copy(MaterialTheme.colors.onBackground),
                )
            },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    onUpdateSection(section.copy(sectionTitle = sectionName))
                    isAddingSection = false
                    sectionName = ""
                }
            ),
            singleLine = true
        )
    } else {
        SwipeableActionsBox(
            startActions = swipeActionItem(
                icon = Icons.Default.Edit,
                color = MaterialTheme.colors.primaryVariant
            ) {
                isAddingSection = true
            },
            endActions = swipeActionItem(
                icon = Icons.Default.Delete,
                color = Color.Red
            ) {
                onDeleteSection(section)
            },
            swipeThreshold = 200.dp,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(15.dp)
                    .border(
                        width = 3.dp,
                        color = MaterialTheme.colors.primaryVariant,
                        shape = RoundedCornerShape(10.dp)
                    )
                    .clickable {
                        onSectionClicked(section)
                    },
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = section.sectionTitle,
                    style = MaterialTheme.typography.h6.copy(MaterialTheme.colors.onBackground),
                    textAlign = TextAlign.Center,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                )
            }
        }
    }
}