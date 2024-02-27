package com.example.courseapp.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Subscriptions
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.courseapp.common.Constants
import com.example.courseapp.student_features.presentation.common_items.BottomNavItem
import com.example.courseapp.student_features.presentation.favorite_page.StudentFavoritePage
import com.example.courseapp.student_features.presentation.home_page.StudentHomePage
import com.example.courseapp.student_features.presentation.profile_page.StudentProfilePage
import com.example.courseapp.student_features.presentation.search_page.StudentSearchPage
import com.example.courseapp.student_features.presentation.subscriptions_page.StudentSubscriptionPage


fun NavGraphBuilder.studentNavGraph(
    navController: NavHostController
) {
    navigation(startDestination = Screens.StudentHomeScreen.route, route = Constants.STUDENT_SCREEN_ROUTE){

        val items = listOf(
            BottomNavItem("Home", icon = Icons.Default.Home,route= Screens.StudentHomeScreen.route),
            BottomNavItem("Search", icon = Icons.Default.Search,route= Screens.StudentSearchScreen.route),
            BottomNavItem("Subscriptions", icon = Icons.Default.Subscriptions,route= Screens.StudentSubscriptionScreen.route),
            BottomNavItem("Favorite", icon = Icons.Default.Favorite,route= Screens.StudentFavoriteScreen.route),
            BottomNavItem("Profile", icon = Icons.Default.AccountCircle,route= Screens.StudentProfileScreen.route)
        )

        composable(route = Screens.StudentHomeScreen.route) {
            StudentHomePage(items,navController = navController)
        }


        composable(route = Screens.StudentSearchScreen.route) {
            StudentSearchPage(items,navController = navController)
        }



        composable(route = Screens.StudentSubscriptionScreen.route) {
            StudentSubscriptionPage(items,navController = navController)
        }


        composable(route = Screens.StudentFavoriteScreen.route) {
            StudentFavoritePage(items,navController = navController)
        }


        composable(route = Screens.StudentProfileScreen.route) {
            StudentProfilePage(items,navController = navController)
        }
    }
}