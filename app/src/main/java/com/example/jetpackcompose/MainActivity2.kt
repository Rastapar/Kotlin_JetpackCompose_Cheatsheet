package com.example.jetpackcompose

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Scaffold
import androidx.compose.material.Snackbar
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myapplication.R
import com.example.myapplication.ui.theme.MyApplicationTheme
import kotlinx.coroutines.launch
import kotlin.random.Random

/*
    The Flows are all about being notified about changes in your code and sending them through a
    pipeline that potentially modifies them.
    A Flow at the end is a coroutine that can emit multiple values over a period of time.
    It is not necessary to use StateFlow with Compose because Compose already comes with a state
    If the screen changes to horizontal, the Activity will be recreated

    ShareFlow is used to send one time events. Not like StaveFlow that updates every time.
 */

class MainActivity2 : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            //rowColumnsAlignment()
            //modifiers()
            //cardComposable()
            //StylingText()
            //StateComponent()
            FieldButtonSnackbar()
        }
    }
}

@Composable
fun rowColumnsAlignment(){
    Column {
        Text(text = "Hello Roman")
        Text(text = "Hello World")
        Row {
            Text(text = "Hello Roman")
            Text(text = "Hello World")
        }
        Column (
            modifier = Modifier
                .background(Color.Yellow)
                .fillMaxHeight(0.5f)
                .width(200.dp),
            horizontalAlignment = Alignment.CenterHorizontally, // Alignment and Arrangement can be swapped
            verticalArrangement = Arrangement.SpaceEvenly
        ){
            Text(text = "Special text")
            Text(text = "Special text 2")
        }
    }
}


@Composable
fun modifiers(){
    Column (
        modifier = Modifier
            .background(Color.Green)
            .fillMaxHeight(0.4f)
            .width(600.dp)
            .requiredWidth(200.dp)  // It sets the minimum width because width can be flexible depending on the screen
            .padding(horizontal = 10.dp)    // Should be enough to set the margin too
            .border(3.dp, Color.Black)      // We go from outside to inside
            .padding(all = 5.dp)
            .border(5.dp, color = Color.Yellow)
            .padding(10.dp)
            .border(8.dp, Color.LightGray)
    ) {
        Text(
            text = "Some Modifier",
            modifier = Modifier
                .offset(x = 20.dp, y = 50.dp)   // Similar to margin but not the same
                .border(3.dp, Color.Red)
                .padding(5.dp)
                .clickable {    // There are more interactive actions

                }
            )
        Spacer(modifier = Modifier.height(50.dp))
        Text(text = "Another one")
    }
}

@Composable
fun cardComposable(){
    val painter = painterResource(id = R.drawable.myflower)
    val description = "A bee in the flower looking for pollen"
    val title = "A bee in a flower"

    Box(
        modifier = Modifier
            .fillMaxWidth(0.5f)
            .padding(16.dp)
    ) {
        ImageCard(painter = painter, contentDescription = description, title = title)
    }
}

// Composable functions should start with capital letter
@Composable
fun ImageCard(
    painter : Painter,
    contentDescription : String,
    title : String,
    modifier : Modifier = Modifier
){
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(15.dp),
        elevation = 5.dp
    ){
        Box(
            modifier = Modifier.height(200.dp)
        ) {
            Image(
                painter = painter,
                contentDescription = contentDescription,
                contentScale = ContentScale.Crop
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                Color.Black
                            ),
                            startY = 300f
                        )
                    )
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(12.dp),
                contentAlignment = Alignment.BottomStart
            ) {
                Text(
                    text = title,
                    style = TextStyle(color = Color.White, fontSize = 16.sp)    // sp format will adjust to the font size of the user phone, and 'dp' won't
                )
            }
        }
    }
}

@Composable
fun StylingText () {
    val fontFamily = FontFamily(
        Font(R.font.lexend_black, FontWeight.Black),
        Font(R.font.lexend_bold, FontWeight.Bold),
        Font(R.font.lexend_extrabold, FontWeight.ExtraBold),
        Font(R.font.lexend_extralight, FontWeight.ExtraLight),
        Font(R.font.lexend_light, FontWeight.Light),
        Font(R.font.lexend_medium, FontWeight.Medium),
        Font(R.font.lexend_regular, FontWeight.Normal),
        Font(R.font.lexend_semibold, FontWeight.SemiBold),
        Font(R.font.lexend_thin, FontWeight.Thin)
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF101010))
    ) {
        Text(
            text = buildAnnotatedString {
                withStyle(
                    style = SpanStyle(
                        color = Color.Green,
                        fontSize = 40.sp
                    )
                ) {
                    append(
                        "J"
                    )
                }
                append("etpack ")
                withStyle(
                    style = SpanStyle(
                        color = Color.Green,
                        fontSize = 40.sp
                    )
                ) {
                    append(
                        "C"
                    )
                }
                append("ompose")
            },
            color = Color.White,
            fontSize = 30.sp,
            fontFamily = fontFamily,
            fontWeight = FontWeight.Bold,
            fontStyle = FontStyle.Italic,
            textAlign = TextAlign.Center,
            textDecoration = TextDecoration.Underline
        )
    }
}

@Composable
fun StateComponent(){
    Column (Modifier.fillMaxSize()) {
        val color = remember {
            mutableStateOf(Color.Yellow)
        }
        ColorBox(
            modifier = Modifier
                .weight(1f)
                .fillMaxSize(),
            updateColor = {
                color.value = it
            }
        )
        Box(
            modifier = Modifier
                .background(color.value)
                .weight(1f)
                .fillMaxSize()
        )
    }
}

// Every time this function is called again, his state is restarted.
// So if we want the state to persist across recomposition, we would need 'remember'
@Composable
fun ColorBox(
    modifier: Modifier = Modifier,
    updateColor: (Color) -> Unit
){

    Box(
        modifier = modifier
            .background(Color.Yellow)
            .clickable {
                updateColor(
                    Color(
                        Random.nextFloat(), // R
                        Random.nextFloat(), // G
                        Random.nextFloat(), // B
                        1f
                    )
                )
            }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun FieldButtonSnackbar(){
    // More complete and native way to add a snackbar
    Snackbar (backgroundColor = Color.LightGray) {
        Text(text = "Hello Dude")
    }
    // More easy way to add a snackbar is the Snackbar
    val scaffoldState = rememberScaffoldState()
    // If we use 'by remember' the value saved is a String
    // If we use '= remember', the value is a MutableState<String>
    var textFieldState by remember {
        mutableStateOf("")
    }
    val scope = rememberCoroutineScope() // The snackbar needs to be launched in a coroutine
    // It is only okay to launch coroutines inside composables if it is for callbacks
    Scaffold (
        modifier = Modifier
            .fillMaxSize(),
        scaffoldState = scaffoldState
    ) {
        Column (
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(30.dp)
        ) {
            // Basic Text Field are less complex and with less options
            TextField(
                value = textFieldState,
                label = {
                    Text("Enter some info dude")
                },
                onValueChange = {
                    textFieldState = it
                },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {
                    scope.launch { 
                        scaffoldState.snackbarHostState.showSnackbar("Hi I am some snackbar: $textFieldState")
                    }
                }
            ) {
                Text(text = "Click me please")
            }
        }
    }
}

