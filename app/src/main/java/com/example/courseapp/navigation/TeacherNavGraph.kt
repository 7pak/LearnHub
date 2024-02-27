package com.example.courseapp.navigation

 import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.courseapp.common.Constants
import com.example.courseapp.teacher_features.presentation.AddCourseScreen
import com.example.courseapp.teacher_features.presentation.AddSectionScreen
import com.example.courseapp.teacher_features.presentation.AddVideoScreen
import com.example.courseapp.teacher_features.presentation.DocumentAndVideosScreen
import com.example.courseapp.teacher_features.presentation.SectionsScreen
import com.example.courseapp.teacher_features.presentation.TeacherHome
import com.example.courseapp.teacher_features.presentation.TeacherProfile


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
            SectionsScreen(navController = navController,sharedViewModel = sharedViewModel)
        }
        composable(route = Screens.DocumentVideosScreen.route){
            DocumentAndVideosScreen(navController = navController,sharedViewModel = sharedViewModel)
        }
    }
}