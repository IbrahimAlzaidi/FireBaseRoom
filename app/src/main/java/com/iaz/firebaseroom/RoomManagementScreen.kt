package com.iaz.firebaseroom

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import java.util.UUID

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RoomManagementScreen(repository: RealtimeDatabaseRepository, navController: NavController) {
    var userId by remember { mutableStateOf("") }
    var roomId by remember { mutableStateOf("") }
    var error by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = userId,
            onValueChange = { userId = it },
            label = { Text("User ID") },
        )
        error?.let {
            Text(
                text = "Error: $it",
                color = Color.Red,
                modifier = Modifier.padding(8.dp)
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                repository.createRoom(userId,
                    onSuccess = { roomId -> navController.navigate("room/$userId/$roomId") },
                    onFailure = { exception -> error = exception.message }
                )
            }
        ) {
            Text(text = "Create Room")
        }
        Spacer(modifier = Modifier.height(16.dp))
        TextField(
            value = roomId,
            onValueChange = { roomId = it },
            label = { Text("Room ID to join") },
        )
        error?.let {
            Text(
                text = "Error: $it",
                color = Color.Red,
                modifier = Modifier.padding(8.dp)
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                val participantIndex = 0
                userId = "100"
                repository.joinRoom(roomId, participantIndex,
                    onSuccess = { navController.navigate("room/$userId/$roomId") },
                    onFailure = { exception ->
                        error = "Failed to join room. Please make sure you entered a valid room ID."
                    }
                )
            }
        ) {
            Text(text = "Join Room")
        }
    }
}