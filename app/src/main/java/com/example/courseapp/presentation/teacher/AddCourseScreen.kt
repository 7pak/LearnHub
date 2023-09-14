package com.example.courseapp.presentation.teacher

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.courseapp.Screens
import com.example.courseapp.data.remote.teacher_post_dto.AddCourseFields
import com.example.courseapp.presentation.login_signUp.common_item.ConfirmButton
import com.example.courseapp.presentation.teacher.common_item.AddMedia
import com.example.courseapp.presentation.teacher.common_item.DropDownMenuEditable
import com.example.courseapp.presentation.teacher.common_item.EditAbleTextField
import com.example.courseapp.presentation.teacher.common_item.addCourse
import com.example.courseapp.presentation.teacher.states.CourseStatesModel
import com.example.courseapp.domain.model.TeacherModel
import com.example.courseapp.navigation.SharedViewModel
import com.example.courseapp.presentation.teacher.common_item.updateCourse
import com.example.courseapp.presentation.teacher.states.add_courses.CourseState
import com.example.courseapp.presentation.ui.theme.CourseAppTheme

@Composable
fun AddCourseScreen(
    courseStateModel: CourseStatesModel = hiltViewModel(),
    teacherModel: TeacherModel = hiltViewModel(),
    navController: NavHostController,
    sharedViewModel: SharedViewModel
) {
    val courseFields: AddCourseFields? = sharedViewModel.courseFields

    LaunchedEffect(key1 = true) {
        if (sharedViewModel.courseFields != null) {
            courseStateModel.updateCourseState(
                CourseState(
                    title = courseFields?.title!!,
                    description = courseFields.description,
                    categoryId = courseFields.category_id,
                    price = courseFields.price,
                    photoUri = courseFields.photo
                )
            )
        }
    }


    val lifecycleOwner = LocalLifecycleOwner.current


    val state by courseStateModel.courseState


    val descriptionCharacters by remember {
        derivedStateOf {
            state.description.length
        }
    }
    val fieldsFilled by remember {
        derivedStateOf {
            state.title.isNotEmpty() && state.description.isNotEmpty() && state.price.isNotEmpty()
                    && state.categoryId != null && state.photoUri != null && descriptionCharacters < 300
        }
    }

    var expanded by remember {
        mutableStateOf(false)
    }
    val categoryCourses =
        mapOf(1 to "php", 2 to "kotlin", 3 to "java", 4 to "laravel", 5 to "python")
    var categoryItem by remember {
        mutableStateOf("")
    }

    courseStateModel.updateCourseState(
        state.copy(
            categoryId = try {
                categoryCourses.entries.find { it.value == categoryItem }!!.key

            } catch (e: NullPointerException) {
                Log.d("ErrorTag", "AddCourseScreen: category is null")
                1
            }
        )
    )

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
                Text(
                    text = "Adding New Course",
                    style = MaterialTheme.typography.h5.copy(
                        color = MaterialTheme.colors.primaryVariant,
                        fontWeight = FontWeight.SemiBold,
                        fontStyle = FontStyle.Italic
                    ),
                    modifier = Modifier.padding(vertical = 20.dp, horizontal = 10.dp)
                )
                Divider(
                    modifier = Modifier
                        .padding(bottom = 20.dp)
                        .background(MaterialTheme.colors.primaryVariant)
                )

            }

            item {

                EditAbleTextField(
                    modifier = Modifier.padding(horizontal = 10.dp),
                    value = state.title,
                    onValueChange = {
                        courseStateModel.updateCourseState(state.copy(title = it))
                    },
                    placeHolder = "Course Title",
                    singleLine = true
                )

                EditAbleTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp)
                        .height(150.dp),
                    value = state.description,
                    onValueChange = {
                        courseStateModel.updateCourseState(state.copy(description = it))
                    },
                    placeHolder = "Course description",
                    maxLines = 5,
                    trailingIcon = {

                        Text(
                            text = "$descriptionCharacters/300",
                            style = MaterialTheme.typography.caption,
                            color = if (descriptionCharacters > 300) {
                                Color.Red
                            } else {
                                MaterialTheme.colors.primaryVariant
                            },
                            modifier = Modifier.wrapContentSize()
                        )

                    }
                )

                EditAbleTextField(
                    modifier = Modifier
                        .padding(horizontal = 10.dp)
                        .width(110.dp),
                    value = state.price,
                    onValueChange = {
                        courseStateModel.updateCourseState(state.copy(price = it))
                    },
                    placeHolder = "price",
                    singleLine = true,
                    trailingIcon = {
                        Icon(
                            imageVector = Icons.Default.AttachMoney,
                            contentDescription = "",
                            tint = MaterialTheme.colors.primaryVariant
                        )
                    }
                )

                DropDownMenuEditable(
                    modifier = Modifier.padding(horizontal = 10.dp),
                    placeHolder = "Category",
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                    items = categoryCourses,
                    onItemSelected = {
                        categoryItem = it
                    },
                    onExpandedChange = {
                        expanded = it
                    }
                )


                AddMedia(modifier = Modifier.padding(vertical = 30.dp), text = "Add Course Image") {
                    courseStateModel.updateCourseState(state.copy(photoUri = it))
                }
            }

            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 20.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    ConfirmButton(
                        text = "Add Course", onClick = {
                            Log.d("AddCourse", "AddCourseScreen: categoryId ${state.categoryId}")
                            if (courseFields?.courseId != null) {
                                 isLoading = true
                                updateCourse(
                                    lifecycleOwner = lifecycleOwner,
                                    teacherModel = teacherModel,
                                    addCourseFields = AddCourseFields(
                                        courseId = courseFields.courseId,
                                        title = state.title,
                                        description = state.description,
                                        price = state.price,
                                        photo = state.photoUri,
                                        category_id = state.categoryId!!
                                    ),onFailed = { isLoading = false }
                                ) {
                                     isLoading = false
                                    teacherModel.fetchAllCourses()
                                    sharedViewModel.updateCourseFields(null)
                                    navController.popBackStack()
                                }
                            } else {
                                isLoading = true
                                addCourse(
                                    lifecycleOwner = lifecycleOwner,
                                    teacherModel = teacherModel,
                                    addCourseFields = AddCourseFields(
                                        title = state.title,
                                        description = state.description,
                                        price = state.price,
                                        photo = state.photoUri,
                                        category_id = state.categoryId!!
                                    ),onFailed = { isLoading = false }
                                ) { courseId ->
                                    isLoading = false
                                    navController.navigate(Screens.AddSectionScreen.passCourseId(id = courseId)) {
                                        popUpTo(Screens.TeacherScreen.route) {
                                            inclusive = false
                                        }
                                    }
                                }
                            }
                        },
                        isEnabled = fieldsFilled, buttonWidth = 200.dp
                    )
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
fun AddCoursePreview() {
    CourseAppTheme {
        //AddCourseScreen()
    }
}