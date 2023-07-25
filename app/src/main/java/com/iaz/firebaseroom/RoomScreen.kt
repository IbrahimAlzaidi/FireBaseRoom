package com.iaz.firebaseroom

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@Composable
fun RoomScreen(userId: String, roomId: String, repository: RealtimeDatabaseRepository) {
    val room = remember { mutableStateOf<Room?>(null) }
    val rotateState = remember { mutableStateOf(0f) }

    LaunchedEffect(roomId) {
        repository.listenRoom(roomId,
            onRoomUpdate = { room.value = it },
            onFailure = { /* handle failure */ }
        )
    }

    room.value?.let {
        val isAdmin = userId == it.admin

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Blue),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Room ID: ${it.id}", color = Color.White)
            Text(
                text = "Participants: ${
                    it.participants.mapIndexedNotNull { index, isParticipant -> if (isParticipant) index else null }
                        .joinToString(", ")
                }", color = Color.White
            )

            Image(
                painter = painterResource(id = R.drawable.ic_launcher_foreground),
                contentDescription = null,
                modifier = Modifier
                    .size(200.dp)
                    .rotate(rotateState.value)
            )

            Button(
                onClick = { if (isAdmin) rotateState.value += 180f },
                enabled = isAdmin
            ) {
                Text("Flip Image", color = Color.White)
            }
        }
    }
}