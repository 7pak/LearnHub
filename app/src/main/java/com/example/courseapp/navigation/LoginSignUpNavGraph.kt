package com.example.courseapp.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.courseapp.common.Constants
import com.example.courseapp.authentication.presentation.login.LoginScreen
import com.example.courseapp.authentication.presentation.signup.SignUpScreen


fun NavGraphBuilder.loginSignUpNavGraph(
    navController: NavHostController
) {
    navigation(startDestination = Screens.LoginScreen.route, route = Constants.LOGIN_SIGNUP_SCREEN_ROUTE){
        composable(route = Screens.LoginScreen.route){
            LoginScreen(navController)
        }
        composable(route = Screens.SignUpScreen.route){
            SignUpScreen(navController)
        }
    }
}