package com.ud.sheltermind

import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.ud.sheltermind.views.LoginCompose
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import com.ud.sheltermind.navigation.AppNavigation

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
                Surface(color = MaterialTheme.colorScheme.background) {
                    AppNavigation()
            }
        }
    }
}

