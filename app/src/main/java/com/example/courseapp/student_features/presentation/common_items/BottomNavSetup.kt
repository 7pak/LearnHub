package com.example.courseapp.student_features.presentation.common_items

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState


data class BottomNavItem(
    val name:String,
    val icon: ImageVector,
    val route:String,
)
@Composable
fun BottomNavigationBar(
    items:List<BottomNavItem>,
    navHostController: NavHostController,
    modifier:Modifier = Modifier,
    onItemClicked:(BottomNavItem)->Unit
) {
    val backStackEntry = navHostController.currentBackStackEntryAsState()
    BottomNavigation (
        modifier = modifier,
        backgroundColor = MaterialTheme.colors.background,
        elevation = 5.dp
    ){
        items.forEach { item ->
            val selected = item.route == backStackEntry.value?.destination?.route
            BottomNavigationItem(selected = selected, onClick = {
                onItemClicked(item)
            },
                selectedContentColor = MaterialTheme.colors.primaryVariant,
                unselectedContentColor = MaterialTheme.colors.primary,
                icon = {
                    Icon(imageVector = item.icon, contentDescription = item.name)
                    if (selected){
                        Text(text = item.name)
                    }
            })
        }
    }
}