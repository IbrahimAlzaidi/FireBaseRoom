package com.iaz.firebaseroom

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

@Composable
fun AppNavigator(repository: RealtimeDatabaseRepository) {
    val navController: NavHostController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Screen.RoomManagement.route
    ) {
        roomManagementRoute(repository, navController)
        roomScreenRoute(repository)
    }
}

fun NavGraphBuilder.roomManagementRoute(
    repository: RealtimeDatabaseRepository,
    navController: NavController
) {
    composable(
        route = Screen.RoomManagement.route,
        arguments = emptyList()
    ) {
        RoomManagementScreen(repository, navController)
    }
}

fun NavGraphBuilder.roomScreenRoute(repository: RealtimeDatabaseRepository) {
    composable(
        route = Screen.RoomScreen.route,
        arguments = listOf(
            navArgument("userId") {
                type = NavType.StringType
                nullable = true
                defaultValue = null
            },
            navArgument("roomId") {
                type = NavType.StringType
                nullable = true
                defaultValue = null
            }
        )
    ) { backStackEntry ->
        RoomScreen(backStackEntry.arguments?.getString("userId")!!, backStackEntry.arguments?.getString("roomId")!!, repository)
    }
}