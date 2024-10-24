package com.ud.sheltermind

import android.os.Build
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.ud.sheltermind.views.LoginCompose
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.annotation.RequiresApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import com.ud.sheltermind.navigation.AppNavigation

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
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

