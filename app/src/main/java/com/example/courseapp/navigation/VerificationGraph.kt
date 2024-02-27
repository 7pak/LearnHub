package com.example.courseapp.navigation

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.example.courseapp.common.Constants
import com.example.courseapp.authentication.presentation.verification.EmailVerificationPage
import com.example.courseapp.authentication.presentation.verification.PasswordResetPage
import com.example.courseapp.authentication.presentation.verification.VerificationPage

fun NavGraphBuilder.verificationGraph(
    navController: NavHostController,
) {
    navigation(startDestination = Screens.VerificationScreen.route, route = Constants.VERIFICATION_ROUTE){
        composable(route = Screens.VerificationScreen.route, arguments = listOf(navArgument(
            name = VERIFICATION_SOURCE
        ){
            type = NavType.StringType
            defaultValue = FROM_SIGNUP
        })){
            var pathValue by rememberSaveable { mutableStateOf(FROM_SIGNUP) }

            pathValue = it.arguments?.getString(VERIFICATION_SOURCE).toString()
            Log.d("NavigationValue", "verificationGraph: $pathValue")

            VerificationPage(navController = navController){
                when(pathValue){
                    FROM_SIGNUP -> {
                        navController.navigate(Constants.TEACHER_SCREEN_ROUTE){
                            popUpTo(Constants.LOGIN_SIGNUP_SCREEN_ROUTE){
                                inclusive = false
                            }
                        }
                    }
                    FROM_EMAIL_VERIFICATION_PAGE -> {
                        navController.navigate(Screens.PasswordResetScreen.route){
                            popUpTo(Constants.LOGIN_SIGNUP_SCREEN_ROUTE){
                                inclusive = false
                            }

                        }
                    }
                }
            }
        }

        composable(route = Screens.EmailVerificationScreen.route){
            EmailVerificationPage(navController = navController)
        }

        composable(route = Screens.PasswordResetScreen.route){
            PasswordResetPage(navController = navController)
        }
    }
}