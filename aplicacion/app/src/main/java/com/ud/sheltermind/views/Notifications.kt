import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NotificationsScreen()
        }
    }
}

@Composable
fun NotificationsScreen() {
    Scaffold(
        bottomBar = { BottomNavigationBar() }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            Text(
                text = "Notifications",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.CenterHorizontally),
                color = Color(0xFF003366) // Color azul oscuro
            )

            Spacer(modifier = Modifier.height(16.dp))

            NotificationCard(title = "Title", description = "Description", date = "DD/MM/AAAA", time = "HH:MM")
            Spacer(modifier = Modifier.height(16.dp))
            NotificationCard(title = "Title", description = "Description", date = "DD/MM/AAAA", time = "HH:MM")
            Spacer(modifier = Modifier.height(16.dp))
            NotificationCard(title = "Title", description = "Description", date = "DD/MM/AAAA", time = "HH:MM")
        }
    }
}

@Composable
fun NotificationCard(title: String, description: String, date: String, time: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(text = title, fontWeight = FontWeight.Bold, color = Color(0xFF003366), fontSize = 18.sp)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = description, color = Color.Gray, fontSize = 14.sp)
            Spacer(modifier = Modifier.weight(1f))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = date, color = Color.Gray, fontSize = 12.sp)
                Text(text = time, color = Color.Gray, fontSize = 12.sp)
            }
        }
    }
}

@Composable
fun BottomNavigationBar() {
    val selectedIndex = remember { mutableStateOf(0) }
    NavigationBar(
        containerColor = Color.White,
        contentColor = Color.Gray,
        tonalElevation = 8.dp
    ) {
        NavigationBarItem(
            selected = selectedIndex.value == 0,
            onClick = { selectedIndex.value = 0 },
            icon = { Icon(Icons.Filled.Home, contentDescription = "Home") },
            label = { Text("Home") }
        )
        NavigationBarItem(
            selected = selectedIndex.value == 1,
            onClick = { selectedIndex.value = 1 },
            icon = { Icon(Icons.Filled.Search, contentDescription = "Explorer") },
            label = { Text("Explorer") }
        )
        NavigationBarItem(
            selected = selectedIndex.value == 3,
            onClick = { selectedIndex.value = 3 },
            icon = { Icon(Icons.Filled.Notifications, contentDescription = "Notification") },
            label = { Text("Notification") }
        )
        NavigationBarItem(
            selected = selectedIndex.value == 4,
            onClick = { selectedIndex.value = 4 },
            icon = { Icon(Icons.Filled.Person, contentDescription = "Perfil") },
            label = { Text("Perfil") }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewNotificationsScreen() {
    NotificationsScreen()
}
