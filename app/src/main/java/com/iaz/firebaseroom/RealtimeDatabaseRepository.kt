package com.iaz.firebaseroom

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.util.UUID


class RealtimeDatabaseRepository {
    private val db = Firebase.database("https://roomtest-8ad3b-default-rtdb.europe-west1.firebasedatabase.app")

    fun createRoom(userId: String, onSuccess: (String) -> Unit, onFailure: (Exception) -> Unit) {
        val newRoomId = UUID.randomUUID().toString()
        db.getReference("rooms").child(newRoomId).setValue(Room(id = newRoomId, admin = userId, participants = listOf(true)))
            .addOnSuccessListener { onSuccess(newRoomId) }
            .addOnFailureListener { e -> onFailure(e) }
    }

    fun joinRoom(roomId: String, participantIndex: Int, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        val roomRef = db.getReference("rooms").child(roomId)
        roomRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    roomRef.child("participants").child(participantIndex.toString()).setValue(true)
                        .addOnSuccessListener { onSuccess() }
                        .addOnFailureListener { e -> onFailure(e) }
                } else {
                    onFailure(Exception("Room does not exist"))
                }
            }

            override fun onCancelled(error: DatabaseError) {
                onFailure(Exception(error.message))
            }
        })
    }
    fun listenRoom(roomId: String, onRoomUpdate: (Room) -> Unit, onFailure: (Exception) -> Unit) {
        db.getReference("rooms")
            .child(roomId)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    snapshot.getValue(Room::class.java)?.let { room ->
                        onRoomUpdate(room)
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    onFailure(Exception(error.message))
                }
            })
    }
}