package com.example.courseapp.student_features.presentation.home_page

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Subscriptions
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.courseapp.navigation.Screens
import com.example.courseapp.student_features.presentation.common_items.BottomNavItem
import com.example.courseapp.student_features.presentation.common_items.BottomNavigationBar

@Composable
fun StudentHomePage(
    items: List<BottomNavItem>,
    navController: NavHostController
) {
    Scaffold (
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            BottomNavigationBar(items =items, navHostController =navController , onItemClicked = {
                Log.d("ErrorSkip", "BottomNavigationBar: $it")
                navController.navigate(it.route)
            })
        }
    ){
        it

        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
            Text(text = "Home")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    StudentHomePage(
        items = listOf(
            BottomNavItem(
                "Home",
                icon = Icons.Default.Home,
                route = Screens.StudentHomeScreen.route
            ),
            BottomNavItem(
                "Search",
                icon = Icons.Default.Search,
                route = Screens.StudentSearchScreen.route
            ),
            BottomNavItem(
                "Subscriptions",
                icon = Icons.Default.Subscriptions,
                route = Screens.StudentSubscriptionScreen.route
            ),
            BottomNavItem(
                "Favorite",
                icon = Icons.Default.Favorite,
                route = Screens.StudentFavoriteScreen.route
            ),
            BottomNavItem(
                "Profile",
                icon = Icons.Default.AccountCircle,
                route = Screens.StudentProfileScreen.route
            )
        ), navController = rememberNavController()
    )
}