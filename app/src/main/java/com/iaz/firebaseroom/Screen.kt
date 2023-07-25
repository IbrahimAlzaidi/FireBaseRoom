package com.iaz.firebaseroom

sealed class Screen(val route: String) {
    object RoomManagement : Screen("roomManagement")
    object RoomScreen : Screen("room/{userId}/{roomId}")
}