package com.example.courseapp.presentation.teacher

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.courseapp.common.Constants
import com.example.courseapp.common.UserVerificationModel
import com.example.courseapp.domain.model.AuthModel
import com.example.courseapp.domain.model.TeacherModel
import com.example.courseapp.presentation.teacher.common_item.EditableProfile
import com.example.courseapp.presentation.ui.theme.CourseAppTheme

@Composable
fun TeacherProfile(
    navController: NavHostController,
    userVerificationModel: UserVerificationModel = hiltViewModel(),
    authModel: AuthModel = hiltViewModel(),
    teacherModel: TeacherModel = hiltViewModel()
) {
    LaunchedEffect(key1 = teacherModel){
        teacherModel.fetchProfileInfo()
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background),
    ) {
        EditableProfile(
            userVerificationModel = userVerificationModel,
            authModel = authModel,
            teacherModel = teacherModel
        ) {
            navController.navigate(Constants.LOGIN_SIGNUP_SCREEN_ROUTE)
        }
    }
}


//TODO YOU HAVE TO EDIT THE STATES IN THE PROFILE MODEL
@Preview(showBackground = true)
@Composable
fun TeacherProfilePreview() {
    CourseAppTheme {
        // TeacherProfileScreen()
    }
}