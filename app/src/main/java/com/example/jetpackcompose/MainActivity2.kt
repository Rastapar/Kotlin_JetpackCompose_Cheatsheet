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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalLifecycleOwner
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
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myapplication.R
import com.example.myapplication.ui.theme.MyApplicationTheme
import kotlinx.coroutines.delay
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
//            FieldButtonSnackbar()
//            ListComponents()
//            ConstraintLayoutComponent()
            EffectHandlers()    // Just theory
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

@Composable
fun ListComponents () {
    val scrollState = rememberScrollState()
    // This scrollable list load all the items at once
    /*Column (
        modifier = Modifier
            .verticalScroll(state = scrollState)
    ) {
        for (i in 1..50) {
            Text(
                text = "Item $i",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 24.dp)

            )
        }
    }*/
    // This scrollable list load only the items on the screen and close ones
    /*LazyColumn {
        items (count = 500) {
            // Every single item
            Text(
                text = "Item $it",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 24.dp)

            )
        }

    }*/
    LazyColumn {
        // We can pass a list to be the items
        itemsIndexed (
            listOf("One", "dunno", "I think", "hello for everyone that is watching this long text")
        ) {index, value ->
            // Every single item
            Text(
                text = "Item ${index}: $value",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 24.dp)
            )
        }
    }
}

@Composable
fun ConstraintLayoutComponent () {
    val constraints = ConstraintSet {
        val greenBox = createRefFor("greenBox")
        val redBox = createRefFor("redBox")
        val guideline = createGuidelineFromTop(0.5f) // horizontal invisible guideline in the middle ( 50% )

        constrain(greenBox) {
            top.linkTo(parent.top)
            start.linkTo(parent.start)
            width = Dimension.value(100.dp)
            height = Dimension.value(100.dp)

        }
        constrain(redBox){
            top.linkTo(parent.top)
            start.linkTo(greenBox.end)
            bottom.linkTo(guideline)
            width = Dimension.value(100.dp)
            height = Dimension.value(100.dp)
        }
    }
    ConstraintLayout(constraints, modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .background(Color.Green)
                .layoutId(layoutId = "greenBox")
        )
        Box(
            modifier = Modifier
                .background(Color.Red)
                .layoutId(layoutId = "redBox")
        )
    }
}

@Composable
fun EffectHandlers() {
    var text by remember {
        mutableStateOf("")
    }

    // If the value of 'text' changes before this coroutine has ended,
    // this coroutine will cancel and start again with the new value
    // If you pass 'true' to 'key1' then it will be executed only once
    LaunchedEffect(key1 = text) {
        delay(1000)
        println("The text is $text")
    }

    // This scope has to used only in callbacks and 'launch effects'
    val scope = rememberCoroutineScope()
    Button(onClick = {
        scope.launch {
            delay(1000)
            println("Hello guys")
        }
    }) {

    }

    // This launchEffect is executed only once
    // If for some reason 'onTimeout' changes its value while 'LaunchedEffect' has been executed (in the delay let's say)
    // then 'LaunchEffect' will continue executing using the new value of 'onTimeout'
    var onTimeout: () -> Unit = {}   // consider this value as a parameter
    val updatedOnTimeout by rememberUpdatedState(newValue = onTimeout)
    LaunchedEffect(key1 = true) {
        delay(3000)
        updatedOnTimeout()
    }

    // When we need to do some cleaning after a callback we would use the 'DisposableEffect'
    val lifecycleOwner = LocalLifecycleOwner.current
    DisposableEffect(key1 = lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if(event == Lifecycle.Event.ON_PAUSE) {
                println("On pause called")
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
           lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    // The 'SideEffect' is executed when the composable (where this function is)
    // is recomposed correctly
    SideEffect {
        println("This composable has been recomposed successfully")
    }

    // A composable that saves its state, it could be done with flows too (but it is more complex)
    @Composable
    fun produceStateDemo(countUpTo: Int): State<Int> {
        return produceState(initialValue = 0) {
            while (value < countUpTo) {
                delay(1000)
                value++
            }
        }
    }

    // In a normal situation when 'onClick' is triggered then the string of 'counterText' is
    // recalculated and then printed
    // With 'derivedStateOf' we cancel that, so when the 'counter2' increases its value, the
    // text that it will show will be with the old value
    @Composable
    fun DerivedStateOfDemo() {
        var counter2 by remember {
            mutableStateOf(0)
        }
        val counterText by derivedStateOf {
            "The counter is $counter2"
        }
        Button(onClick = { counter2++ }) {
            Text(text = counterText)
        }
    }
}

