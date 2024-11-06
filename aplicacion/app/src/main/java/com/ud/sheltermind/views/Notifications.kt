import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ud.sheltermind.R
import com.ud.sheltermind.componentes.CustomBottomBar
import com.ud.sheltermind.componentes.FieldFormString
import com.ud.sheltermind.enums.EnumNavigation

@Preview
@Composable
fun ViewNotificationsCompose() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = EnumNavigation.Notifications.toString()
    )
    {
        composable(EnumNavigation.Notifications.toString()) {
            NotificationsCompose(navController)
        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun NotificationsCompose(navController: NavController) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        stringResource(R.string.notification_title),
                        style = TextStyle(
                            fontSize = 25.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = Color(0xFF002366)
                        )
                    )
                })
        },bottomBar = { CustomBottomBar(navController = navController) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            NotificationCard()
        }

    }
}


@Composable
fun NotificationCard() {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        items(4) {
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
                    Text(
                        stringResource(R.string.notification),
                        style = TextStyle(
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Normal,
                            textAlign = TextAlign.Center ,
                            color = Color(0xFF002366)
                        )
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        stringResource(R.string.description_notification), style = TextStyle(
                            color = Color.Gray, fontSize = 14.sp
                        )
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("DD/MM/AAAA", color = Color.Gray, fontSize = 12.sp)
                        Text("HH:MM", color = Color.Gray, fontSize = 12.sp)
                    }
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
        }
    }

}
