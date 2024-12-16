package com.ud.sheltermind.componentes

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.widget.DatePicker
import android.widget.TimePicker
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.ud.sheltermind.R
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@Composable
fun SocialNetwork(onClick: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            stringResource(R.string.social_network),
            style = TextStyle(
                fontSize = 15.sp,
                fontWeight = FontWeight.Normal,
                //color = Color.Blue
            )
        )
        Spacer(Modifier.height(4.dp))
        GoogleButton(onClick = onClick)
    }
}

@Composable
fun PassFlied(password: MutableState<String>, label: String) {
    val flag = remember {
        mutableStateOf(false)
    }
    OutlinedTextField(
        value = password.value,
        onValueChange = { password.value = it },
        label = { Text(label) },
        visualTransformation = if (flag.value) VisualTransformation.None else PasswordVisualTransformation(),
        trailingIcon = {
            val image = if (flag.value)
                Icons.Filled.VisibilityOff
            else Icons.Filled.Visibility

            IconButton(onClick = { flag.value = !flag.value }) {
                Icon(
                    imageVector = image,
                    contentDescription = if (flag.value) "Hide password" else "Show password"
                )
            }
        }
    )
}

//Google button
@OptIn(ExperimentalCoilApi::class)
@Composable
fun GoogleButton(onClick: () -> Unit) {
    Button(
        onClick = onClick,
        shape = CircleShape,
        colors = ButtonDefaults.buttonColors(containerColor = Color.White),
        modifier = Modifier.size(70.dp) // Tamaño del botón redondo
    ) {
        Image(
            painter = rememberImagePainter("https://upload.wikimedia.org/wikipedia/commons/thumb/archive/c/c1/20200429221626%21Google_%22G%22_logo.svg/118px-Google_%22G%22_logo.svg.png"),
            contentDescription = "Google Logo",
            modifier = Modifier.size(60.dp) // Tamaño del logo dentro del botón
        )
        Text(text = "Google")
    }
}

@Composable
fun ButtonForm(onClick: () -> Unit, text: String) {
    ElevatedButton(
        onClick = onClick,
        modifier = Modifier
            .width(280.dp),
        shape = RoundedCornerShape(10.dp)
    ) {
        Text(
            text,
            style = TextStyle(
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF002366)
            )
        )

    }
}

@Composable
fun FieldFormString(variable: MutableState<String>, label: String) {
    OutlinedTextField(
        value = variable.value,
        onValueChange = { variable.value = it },
        label = { Text(label) }
    )
}

//agregfo una nuevo componente de FieldFormStrings para la vista Discaver( no se si al moficar FieldFormString modifique el de otras vistas)
@Composable
fun FieldFormStrings(state: MutableState<String>, placeholder: String) {
    TextField(
        value = state.value,
        onValueChange = { newValue -> state.value = newValue },
        placeholder = { Text(placeholder) },
        modifier = Modifier.fillMaxWidth(),
        singleLine = true
    )
}

@Composable
fun NumberField(number: MutableState<String>, label: String) {
    OutlinedTextField(
        value = number.value,
        onValueChange = {
            // Actualiza el valor solo si es un número válido
            if (it.all { char -> char.isDigit() }) {
                number.value = it
            }
        },
        label = { Text(label) },
        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
    )
}

@Composable
fun IconButtonForm(onClick: () -> Unit, icon: ImageVector, sizeval: Dp) {
    IconButton(
        onClick = onClick,
        Modifier.size(sizeval)
    ) {
        Icon(icon, contentDescription = null, Modifier.fillMaxSize())
    }
}

@Composable
fun TextButtonForm(onClick: () -> Unit, text: String) {
    TextButton(onClick = onClick) {
        Text(
            text,
            style = TextStyle(
                fontSize = 15.sp,
                fontWeight = FontWeight.Normal,
                color = Color(0xFF002366)
            )
        )
    }
}

@Composable
fun FieldDateForm(selectedDate: MutableState<String>) {
    val context = LocalContext.current
    val calendar = Calendar.getInstance()
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)

    val datePickerDialog = DatePickerDialog(
        context,
        { _: DatePicker, selectedYear: Int, selectedMonth: Int, selectedDay: Int ->
            selectedDate.value = "$selectedDay/${selectedMonth + 1}/$selectedYear"
        }, year, month, day
    )

    Column {
        OutlinedTextField(
            value = selectedDate.value,
            onValueChange = { selectedDate.value = it },
            label = { Text("Seleccionar fecha") },
            readOnly = true,
            trailingIcon = {
                IconButtonForm(
                    onClick = { datePickerDialog.show() },
                    icon = Icons.Filled.CalendarMonth,
                    sizeval = 20.dp
                )
            },
        )
    }
}

@SuppressLint("DefaultLocale")
@Composable
fun FieldHourForm(selectedTime: MutableState<String>) {
    val context = LocalContext.current
    val calendar = Calendar.getInstance()
    val hour = calendar.get(Calendar.HOUR_OF_DAY)
    val minute = calendar.get(Calendar.MINUTE)

    val timePickerDialog = TimePickerDialog(
        context,
        { _: TimePicker, selectedHour: Int, selectedMinute: Int ->
            selectedTime.value = String.format("%02d:%02d", selectedHour, selectedMinute)
        }, hour, minute, true
    )

    Column {
        OutlinedTextField(
            value = selectedTime.value,
            onValueChange = { selectedTime.value = it },
            label = { Text("Seleccionar hora") },
            readOnly = true,
            trailingIcon = {
                IconButton(onClick = { timePickerDialog.show() }) {
                    Icon(Icons.Filled.AccessTime, contentDescription = "Seleccionar hora")
                }
            }
        )
    }
}
