package com.iaz.firebaseroom

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.iaz.firebaseroom.ui.theme.FireBaseRoomTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FireBaseRoomTheme {
                val realtimeDatabaseRepository = RealtimeDatabaseRepository()
                AppNavigator(repository = realtimeDatabaseRepository)
            }
        }
    }
}