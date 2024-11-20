package com.example.eliteapartments.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.eliteapartments.ui.screens.*
import com.example.eliteapartments.viewmodel.PropertyViewModel
import com.example.eliteapartments.viewmodel.ThemeViewModel

@Composable
fun AppNavigation(
    propertyViewModel: PropertyViewModel,
    themeViewModel: ThemeViewModel,
    modifier: Modifier = Modifier
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "splash",
        modifier = modifier
    ) {
        composable("splash") { SplashScreen(navController) }
        composable("main") {
            MainScreen(
                navController = navController,
                themeViewModel = themeViewModel,
                propertyViewModel = propertyViewModel
            )
        }
        composable("add_property") { AddPropertyForm(navController, propertyViewModel) }
        composable("select_location") {
            MapScreen(navController, propertyViewModel)
        }
        composable(
            route = "property_detail/{propertyId}",
            arguments = listOf(navArgument("propertyId") { type = NavType.IntType })
        ) { backStackEntry ->
            val propertyId = backStackEntry.arguments?.getInt("propertyId") ?: 0
            val properties = propertyViewModel.properties.collectAsState().value
            val property = properties.find { it.id == propertyId }

            property?.let {
                PropertyDetailScreen(navController = navController, property = it)
            }
        }
    }
}
