package com.ud.sheltermind.componentes

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


@Composable
fun Cards (){

}

@Preview
@Composable
fun ViewWeekCompose(){
    WeekCompose()
}

@Composable
fun WeekCompose(){
    Column(Modifier.fillMaxWidth()) {
        Row {
            Spacer(Modifier.width(50.dp))
            Text("L")
            Spacer(Modifier.width(50.dp))
            Text("M")
            Spacer(Modifier.width(50.dp))
            Text("M")
            Spacer(Modifier.width(50.dp))
            Text("J")
            Spacer(Modifier.width(50.dp))
            Text("V")
            Spacer(Modifier.width(50.dp))
            Text("S")
            Spacer(Modifier.width(50.dp))
            Text("D")
            Spacer(Modifier.width(50.dp))
        }

    }
}