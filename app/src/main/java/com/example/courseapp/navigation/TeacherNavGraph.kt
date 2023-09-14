package com.example.courseapp.navigation

import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.courseapp.Screens
import com.example.courseapp.common.Constants
import com.example.courseapp.presentation.teacher.AddCourseScreen
import com.example.courseapp.presentation.teacher.AddSectionScreen
import com.example.courseapp.presentation.teacher.AddVideoScreen
import com.example.courseapp.presentation.teacher.DocumentAndVideosScreen
import com.example.courseapp.presentation.teacher.SectionsScreen
import com.example.courseapp.presentation.teacher.TeacherHome
import com.example.courseapp.presentation.teacher.TeacherProfile


fun NavGraphBuilder.teacherNavGraph(
    navController: NavHostController,
    sharedViewModel:SharedViewModel
) {
    navigation(startDestination = Screens.TeacherScreen.route, route = Constants.TEACHER_SCREEN_ROUTE){
        composable(route = Screens.TeacherScreen.route){
            TeacherHome(navController = navController, sharedViewModel = sharedViewModel)
        }
        composable(route = Screens.TeacherProfileScreen.route){
            TeacherProfile(navController = navController)
        }
        composable(route = Screens.AddCourseScreen.route){
            AddCourseScreen(navController = navController,sharedViewModel = sharedViewModel)
        }
        composable(route = Screens.AddSectionScreen.route){
            AddSectionScreen(navController = navController, sharedViewModel = sharedViewModel)
        }
        composable(route = Screens.AddVideoScreen.route){
            AddVideoScreen(navController = navController, sharedViewModel =sharedViewModel)
        }
        composable(route = Screens.SectionsScreen.route){
            SectionsScreen(navController = navController)
        }
        composable(route = Screens.VideoScreen.route){
            DocumentAndVideosScreen(navController = navController,sharedViewModel = sharedViewModel)
        }
    }
}