package com.example.courseapp.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.example.courseapp.common.Constants
import com.example.courseapp.presentation.teacher.states.CourseStatesModel

@Composable
fun NavGraph(navController:NavHostController,starDestination:String?) {
    val sharedViewModel:SharedViewModel = viewModel()
    if (starDestination != null) {
        NavHost(navController = navController, startDestination = starDestination){
            loginSignUpNavGraph(navController = navController)
            teacherNavGraph(navController = navController,sharedViewModel =sharedViewModel)
            studentNavGraph(navController = navController)
            verificationGraph(navController = navController)
        }
    }
}