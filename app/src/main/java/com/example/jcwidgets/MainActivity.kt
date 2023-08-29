package com.example.jcwidgets

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.example.jcwidgets.ui.theme.JcWidgetsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JcWidgetsTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    PageDropdownMenu()
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    JcWidgetsTheme {
        PageDropdownMenu()
    }
}

@Composable
fun PageDropdownMenu() {
    val menuStartingControl = remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Box{
            Button(onClick = {
                menuStartingControl.value = true
            }) {
                Text(text = "Show")
            }

            DropdownMenu(
                expanded = menuStartingControl.value,
                onDismissRequest = {
                    menuStartingControl.value = false
                }) {
                DropdownMenuItem(
                    text = { Text(text = "Delete") },
                    onClick = { menuStartingControl.value = false })

                DropdownMenuItem(
                    text = { Text(text = "Update") },
                    onClick = { menuStartingControl.value = false })
            }
        }
    }
}

@Composable
fun PageImage() {
    val activity = (LocalContext.current as Activity)

    Image(bitmap = ImageBitmap.imageResource(id =
        activity.resources.getIdentifier(
            "fenerbahce",
            "drawable",
            activity.packageName
        )
    ), contentDescription = "Image")
}

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun PageWebView() {
    var url = "https://fenerium.com/"
    AndroidView(factory = {
        WebView(it).apply {
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            webViewClient = WebViewClient()
            loadUrl(url)
        }
    }, update = {
        it.loadUrl(url)
    })
}

@Composable
fun PageProgressAndSlider() {
    val progressState = remember { mutableStateOf(false) }
    val sliderValue = remember { mutableStateOf(0f) }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        if(progressState.value){
            CircularProgressIndicator(color = Color.Red)
        }

        Row (modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(onClick = {
                progressState.value = true
            }) {
                Text(text = "Start")
            }

            Button(onClick = {
                progressState.value = false
            }) {
                Text(text = "Stop")
            }
        }
        Text(text = "Result : ${sliderValue.value.toInt()}")

        Slider(
            value = sliderValue.value,
            onValueChange = {
                sliderValue.value = it
            },
            valueRange = 0f..100f,
            modifier = Modifier.padding(all = 20.dp),
            colors = SliderDefaults.colors(
                thumbColor = Color.Yellow,
                activeTrackColor = Color.Blue,
                inactiveTrackColor = Color.Black
            )
        )
    }
}

@Composable
fun PageRadioButton() {
    val teamList = listOf("Real Madrid","Fenerbahçe")
    val selectedIndex = remember { mutableStateOf(0) }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Column {
            teamList.forEachIndexed { index, team ->
                Row (modifier = Modifier.clickable {
                    selectedIndex.value = index
                    Log.e("RadioButtonState",team)
                }) {
                    RadioButton(
                        selected = (team == teamList[selectedIndex.value]),
                        onClick = {
                            selectedIndex.value = index
                            Log.e("RadioButtonState",team)
                        })
                    Text(text = team)
                }
            }
        }

        Button(onClick = {
            Log.e("RadioButtonLastState",teamList[selectedIndex.value])
        }) {
            Text(text = "Show")
        }
    }
}

@Composable
fun PageGesture() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Box(modifier = Modifier
            .size(100.dp)
            .background(color = Color.Black)
            .pointerInput(Unit) {
                detectTapGestures(
                    onTap = {
                        Log.e("BoxGesture", "Clicked")
                    },
                    onDoubleTap = {
                        Log.e("BoxGesture", "Double Clicked")
                    },
                    onLongPress = {
                        Log.e("BoxGesture", "Long Pressed")
                    }
                )
            }
        )
    }
}

@Composable
fun PageClicking() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Box(modifier = Modifier
            .size(100.dp)
            .background(color = Color.Black)
            .clickable {
                Log.e("BoxClickable", "Clicked")
            }
        )
    }
}

@Composable
fun PageCheckBox() {
    val checkboxState = remember { mutableStateOf(false) }
    
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row {
            Checkbox(
                checked = checkboxState.value,
                onCheckedChange = {
                    checkboxState.value = it
                    Log.e("CheckboxState",it.toString())
            },
                colors = CheckboxDefaults.colors(
                    checkedColor = Color.Red
                )
            )
            Text(text = "Oguzhan B.", modifier = Modifier.padding(start = 10.dp))
        }
        
        Button(onClick = { 
            Log.e("CheckBoxLastState",checkboxState.value.toString())
        }) {
            Text(text = "Show")
        }
    }
}

@Composable
fun PageSwitch() {
    val switchState = remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Switch(
            checked = switchState.value,
            onCheckedChange = {
                switchState.value = it
                Log.e("SwitchState",it.toString())
            },
            colors = SwitchDefaults.colors(
                checkedTrackColor = Color.Blue,
                checkedThumbColor = Color.Yellow,
                uncheckedTrackColor = Color.Green,
                uncheckedThumbColor = Color.Black
            )
        )
        
        Button(onClick = {
            Log.e("SwitchLastState",switchState.value.toString())
        }) {
            Text(text = "Show")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun PageFab() {
    Scaffold(
        content = {
                  // TODO: Content block
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = {
                    Log.e("FabButton","Clicked")
                },
                text = {
                       Text(text = "Add", color = Color.Blue)
                },
                containerColor = Color.Yellow,
                icon = {
                    Icon(
                        painter = painterResource(id = R.drawable.add_image),
                        contentDescription = "Fab Icon", tint = Color.Blue)
                }
            )
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PageButtonTextField() {
    val tf = remember { mutableStateOf("") }
    val tfOutlined = remember { mutableStateOf("") }
    val inputData = remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Input Data : ${inputData.value}",
            color = Color.Yellow,
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            style = TextStyle(
                background = Color.Blue
            )
        )

        TextField(
            value = tf.value,
            onValueChange = {tf.value = it},
            label = { Text(text ="Enter data")},
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color.Gray,
                textColor = Color.Red,
                focusedIndicatorColor = Color.Green,
                focusedLabelColor = Color.Yellow
            ),
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        Button(
            onClick = {
            inputData.value = tf.value
        },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Blue,
                contentColor = Color.Yellow
            ),
            border = BorderStroke(1.dp,Color.Magenta),
            shape = RoundedCornerShape(50)
        ) {
            Text(text = "Take the data")
        }

        OutlinedTextField(
            value = tfOutlined.value,
            onValueChange = {tfOutlined.value = it},
            label = { Text(text ="Enter data")})

        OutlinedButton(onClick = {
            inputData.value = tfOutlined.value
        },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Blue,
                contentColor = Color.Yellow
            ),
            border = BorderStroke(1.dp,Color.Magenta),
            shape = RoundedCornerShape(50)
        ) {
            Text(text = "Take the data")
        }
    }
}