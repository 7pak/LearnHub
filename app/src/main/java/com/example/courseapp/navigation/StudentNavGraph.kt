package com.example.courseapp.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.navigation
import com.example.courseapp.Screens
import com.example.courseapp.common.Constants


fun NavGraphBuilder.studentNavGraph(
    navController: NavHostController
) {
    navigation(startDestination = Screens.StudentScreen.route, route = Constants.STUDENT_SCREEN_ROUTE){

    }
}