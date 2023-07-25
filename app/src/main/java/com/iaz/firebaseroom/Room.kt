package com.iaz.firebaseroom

data class Room(
    val id: String = "",
    val admin: String = "",
    val participants: List<Boolean> = emptyList())