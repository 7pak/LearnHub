package com.example.courseapp.student_features.presentation.search_page

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.example.courseapp.student_features.presentation.common_items.BottomNavItem
import com.example.courseapp.student_features.presentation.common_items.BottomNavigationBar

@Composable
fun StudentSearchPage(
    items: List<BottomNavItem>,
    navController: NavHostController
) {
    Scaffold (
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            BottomNavigationBar(items =items, navHostController =navController , onItemClicked = {
                navController.navigate(it.route)
            })
        }
    ){
        it


    }
}