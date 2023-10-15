package com.example.jetpackcompose

import android.Manifest
import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.view.MotionEvent
import android.view.animation.OvershootInterpolator
import android.window.SplashScreen
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Badge
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.BottomSheetValue
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Slider
import androidx.compose.material.Snackbar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.material.rememberBottomSheetState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.graphics.PaintingStyle.Companion.Stroke
import androidx.compose.ui.graphics.PointMode
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.boundsInWindow
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalContext
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
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension
import androidx.constraintlayout.compose.ExperimentalMotionApi
import androidx.constraintlayout.compose.MotionLayout
import androidx.constraintlayout.compose.MotionScene
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.myapplication.R
import com.example.myapplication.ui.theme.MyApplicationTheme
import com.google.accompanist.permissions.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import kotlin.math.PI
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.roundToInt
import kotlin.math.sin
import kotlin.random.Random
import kotlin.reflect.KProperty

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
//            rowColumnsAlignment()
//            modifiers()
//            cardComposable()
//            StylingText()
//            StateComponent()
//            FieldButtonSnackbar()
//            ListComponents()
//            ConstraintLayoutComponent()
//            EffectHandlers()    // Just theory
//            SimpleAnimations()
//            CircularProgressBar()
//            DraggableMusicKnob()
//            RoundTimer()
//            AnimatedDropDown()
//            BasicNavigation()
//            AnimatedSplashScreen()
//            BottomNavigationChapter()
//            MultiSelectList()
//            CleanTheming()
//            EasierNavigation()
//            SupportAllScreens()
//            AnimationMotionLayout()
//            PaginationCompose()
//            BottomSheet()
            NavigationDrawer()
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

@Composable
fun SimpleAnimations(){
    var sizeState by remember {
        mutableStateOf(200.dp)
    }
    /*val size by animateDpAsState(
        targetValue = sizeState,
        label = "animation label",
        animationSpec = tween(
            durationMillis = 3000,
            delayMillis = 500,
            easing = LinearOutSlowInEasing
        )
    )*/
    /*val size by animateDpAsState(
        targetValue = sizeState,
        label = "animation label",
        animationSpec = spring(
            Spring.DampingRatioHighBouncy
        )
    )*/
    // The most low level option is this one
    /*val size by animateDpAsState(
        targetValue = sizeState,
        label = "animation label",
        animationSpec = keyframes {
            durationMillis = 5000
            sizeState at 0 with LinearEasing
            sizeState * 1.5f at 1000 with FastOutLinearInEasing
            sizeState * 2f at 5000
        }
    )*/
    val size by animateDpAsState(
        targetValue = sizeState,
        label = "animation label",
        animationSpec = tween(
            durationMillis = 1000
        )
    )
    val infiniteTransition = rememberInfiniteTransition(label = "")
    val color by infiniteTransition.animateColor(
        label = "",
        initialValue = Color.Red,
        targetValue = Color.Green,
        animationSpec = infiniteRepeatable(
            tween(durationMillis = 2000),
            repeatMode = RepeatMode.Reverse
        )
    )
    Box(
        modifier = Modifier
            .size(size)
            .background(color),
        contentAlignment = Alignment.Center
    ) {
        Button(
            onClick = {
                sizeState += 50.dp
            }
        ) {
            Text("Increase Size")
        }

    }
}


@Composable
fun CircularProgressBar() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
    ) {
        CircularProgressBarComponent(
            percentage = 0.8f,
            number = 100    // number inside the progress bar
        )
    }
}

@Composable
fun CircularProgressBarComponent(
    percentage: Float,
    number: Int,
    fontSize: TextUnit = 28.sp,
    radius: Dp = 50.dp,
    color: Color = Color.Green,
    strokeWidth: Dp = 8.dp,
    animDuration: Int = 1000,
    animDelay: Int = 0
) {
    var animationPlayed by remember {
        mutableStateOf(false)
    }
    val currentPercentage = animateFloatAsState(
        label = "",
        targetValue = if (animationPlayed) percentage else 0f,
        animationSpec = tween(
            durationMillis = animDuration,
            delayMillis = animDelay
        )
    )
    LaunchedEffect(key1 = true) {
        animationPlayed = true
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .size(radius * 2f)
    ) {
        Canvas(
            modifier = Modifier
                .size(radius * 2f),
        ) {
            drawArc(
                color = color,
                startAngle = -90f,
                sweepAngle = 360 * currentPercentage.value,
                useCenter = false,
                style = Stroke(strokeWidth.toPx(), cap = StrokeCap.Round)
            )
        }
        Text(
            text = (currentPercentage.value * number).toInt().toString(),
            color = Color.Black,
            fontSize = fontSize,
            fontWeight = FontWeight.Bold
        )
    }
}


@Composable
fun DraggableMusicKnob() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF101010))
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .border(1.dp, Color.Green, RoundedCornerShape(10.dp))
                .padding(30.dp)
        ) {
            var volume by remember {
                mutableStateOf(0f)
            }
            var barCount = 20
            DraggableMusicKnobComponent(
                modifier = Modifier.size(100.dp)
            ) {
                volume = it
            }
            Spacer(modifier = Modifier.width(20.dp))
            DraggableMusicBarComponent(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(30.dp),
                activeBars = (barCount * volume).roundToInt(),
                barCount = barCount
            )
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun DraggableMusicKnobComponent(
    modifier: Modifier,
    limitAngle: Float = 25f,
    onValueChange: (Float) -> Unit
) {
    var rotation by remember {
        mutableFloatStateOf(limitAngle)
    }
    var touchX by remember {
        mutableFloatStateOf(0f)
    }
    var touchY by remember {
        mutableFloatStateOf(0f)
    }
    var centerX by remember {
        mutableFloatStateOf(0f)
    }
    var centerY by remember {
        mutableFloatStateOf(0f)
    }
    
    Image(
        painter = painterResource(id = R.drawable.music_knob),
        contentDescription = "Music Knob",
        modifier = modifier
            .fillMaxSize()
            .onGloballyPositioned {
                val windowBounds = it.boundsInWindow()
                centerX = windowBounds.size.width / 2f
                centerY = windowBounds.size.height / 2f

            }
            .pointerInteropFilter { event ->
                touchX = event.x
                touchY = event.y
                val angle = -atan2(centerX - touchX, centerY - touchY) * (180f / PI).toFloat()
                when (event.action) {
                    MotionEvent.ACTION_DOWN,
                    MotionEvent.ACTION_MOVE -> {
                        if (angle !in -limitAngle..limitAngle) {
                            val fixedAngle = if (angle in -180f..-limitAngle) {
                                360f + angle
                            } else {
                                angle
                            }
                            rotation = fixedAngle
                            val percent = (fixedAngle - limitAngle) / (360f - 2 * limitAngle)
                            onValueChange(percent)
                            true

                        } else false
                    }

                    else -> false
                }
            }
            .rotate(rotation)
    )
}

@Composable
fun DraggableMusicBarComponent (
    modifier: Modifier = Modifier,
    activeBars: Int = 0,
    barCount: Int = 10
) {
    BoxWithConstraints (
        contentAlignment = Alignment.Center,
        modifier = modifier
    ) {
        val barWidth = remember {
            constraints.maxWidth / (2f * barCount)
        }
        Canvas(modifier = modifier) {
            for (i in 0 until barCount) {
                drawRoundRect(
                    color = if (i in 0..activeBars) Color.Green else Color.Gray,
                    topLeft = Offset(i * barWidth * 2f + barWidth / 2f, 0f),
                    size = Size(barWidth, constraints.maxHeight.toFloat()),
                    cornerRadius = CornerRadius(0f)
                )

            }
        }
    }
}

@Composable
fun RoundTimer(){
    val secondsTimer = 10L
    Surface (
        color = Color(0xFF101010),
        modifier = Modifier.fillMaxSize()
    ){
        Box(
            contentAlignment = Alignment.Center
        ) {
            RoundTimerComponent(
                totalTime = secondsTimer * 1000,
                handleColor = Color.Green,
                inactiveBarColor = Color.DarkGray,
                activeBarColor = Color(0xFF37B900),
                modifier = Modifier.size(200.dp)
            )
        }
    }
}

@Composable
fun RoundTimerComponent (
    totalTime: Long,
    handleColor: Color,
    inactiveBarColor: Color,
    activeBarColor: Color,
    modifier: Modifier,
    initialValue: Float = 1f,
    strokeWidth: Dp = 5.dp
) {
    var size by remember {
        mutableStateOf(IntSize.Zero)
    }
    var value by remember {
        mutableFloatStateOf(initialValue)
    }
    var currentTime by remember {
        mutableLongStateOf(totalTime)
    }
    var isTimeRunning by remember {
        mutableStateOf(false)
    }
    /*
    When the user presses the start button, 'isTimeRunning' will become true and as some value
    changed, the block will be executed.
    After the timer is on, the 'currentTime' will be counting down and as the value itself will
    be changing then 'LaunchedEffect' will be calling itself until the timer ends.
    We subtract 100 milliseconds from the 'currentTime' to compensate the delay
    */
    LaunchedEffect(key1 = currentTime, key2 = isTimeRunning) {
        if (currentTime > 0 && isTimeRunning) {
            delay(100)
            currentTime -= 100
            value = currentTime / totalTime.toFloat()
        }
    }
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .onSizeChanged {
                size = it
            }
    ) {
        Canvas(
            modifier = modifier
        ) {
            drawArc(
                color = inactiveBarColor,
                startAngle = -215f,
                sweepAngle = 250f,
                useCenter = false,
                topLeft = Offset.Zero,
                size = Size(size.width.toFloat(), size.height.toFloat()),
                style = Stroke(strokeWidth.toPx(), cap = StrokeCap.Round)
            )
            drawArc(
                color = activeBarColor,
                startAngle = -215f,
                sweepAngle = 250f * value,
                useCenter = false,
                topLeft = Offset.Zero,
                size = Size(size.width.toFloat(), size.height.toFloat()),
                style = Stroke(strokeWidth.toPx(), cap = StrokeCap.Round)
            )
            val center = Offset(size.width / 2f, size.height / 2f)
            val beta = (250f * value + 145f) * (PI / 180f).toFloat()
            val r = size.width / 2f
            val a = cos(beta) * r
            val b = sin(beta) * r
            drawPoints(
                listOf(Offset(center.x + a, center.y + b)),
                pointMode = PointMode.Points,
                color = handleColor,
                strokeWidth = (strokeWidth * 3f).toPx(),
                cap = StrokeCap.Round
            )
        }
        Text(
            text = (currentTime / 1000).toString(),
            fontSize = 44.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
        Button(
            onClick = {
                if(currentTime <= 0) {
                    currentTime = totalTime
                    isTimeRunning = true
                } else {
                    isTimeRunning = !isTimeRunning
                }
            },
            modifier = Modifier
                .align(Alignment.BottomCenter),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = if(!isTimeRunning || currentTime <= 0) {
                    Color.Green
                } else {
                    Color.Red
                }
            )
        ) {
            Text(
                text = if (isTimeRunning && currentTime > 0) "Stop"
                        else if(!isTimeRunning && currentTime > 0) "Start"
                        else "Restart"
            )
        }
    }
}


@Composable
fun AnimatedDropDown() {
    Surface (
        color = Color(0xFF101010),
        modifier = Modifier.fillMaxSize()
    ) {
        AnimatedDropDownComponent(
            text = "My Animated DropDown",
            modifier = Modifier.padding(15.dp),
            initiallyOpened = false
        ) {
            Text(
                text = "This text has been revealed",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .background(Color.Green)
            )
        }
    }
}

@Composable
fun AnimatedDropDownComponent(
    text: String,
    modifier: Modifier,
    initiallyOpened: Boolean,
    content: @Composable () -> Unit
) {
    var isOpen by remember {
        mutableStateOf(initiallyOpened)
    }
    val alpha = animateFloatAsState(
        targetValue = if(isOpen) 1f else 0f,
        animationSpec = tween(
            durationMillis = 300
        ),
        label = ""
    )
    val rotateX = animateFloatAsState(
        targetValue = if(isOpen) 0f else -90f,
        animationSpec = tween(
            durationMillis = 300
        ),
        label = ""
    )
    Column (
        modifier = modifier
            .fillMaxWidth()
    ){
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(
                text = text,
                color = Color.White,
                fontSize = 16.sp
            )
            Icon(
                imageVector = Icons.Default.ArrowDropDown,
                contentDescription = "Open or close the drop down",
                tint = Color.White,
                modifier = Modifier
                    .clickable {
                        isOpen = !isOpen
                    }
                    .scale(1f, if (isOpen) -1f else 1f)
            )
        }
        Spacer(modifier = Modifier.height(10.dp))
        Box (
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxWidth()
                .graphicsLayer {
                    transformOrigin = TransformOrigin(0.5f, 0f)
                    rotationX = rotateX.value
                }
                .alpha(alpha.value)
        ) {
            content()
        }
    }
}


@Composable
fun BasicNavigation() {
    // This navigation used mandatory argument
    // So if no argument is passed from the Text Field, the app crashes
    Navigation()
}


@Composable
fun AnimatedSplashScreen() {
    Surface(
        color = Color(0xFF202020),
        modifier = Modifier.fillMaxSize()
    ) {


        val navController = rememberNavController()
        NavHost(
            navController = navController,
            startDestination = "splash_screen"
        ) {
            composable("splash_screen") {
                SplashScreenComponent(navController = navController)
            }
            composable("main_screen") {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "Hello, welcome to the Main Screen", color = Color.White)
                }
            }
        }
    }
}

@Composable
fun SplashScreenComponent(navController: NavController) {
    val scale = remember {
        Animatable(0f)  // Starting size of the Image
    }
    LaunchedEffect(
        key1 = true
    ) {
        scale.animateTo(
            targetValue = 1f,   // Final size of the Image
            animationSpec = tween(
                durationMillis = 500,
                easing = {
                    OvershootInterpolator(2f).getInterpolation(it)
                }
            )
        )
        delay(3000)
        navController.navigate("main_screen")
    }
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.github_mark_white),
            contentDescription = "Logo of Github",
            modifier = Modifier.scale(scale.value)
        )
    }
}


@Composable
fun BottomNavigationChapter() {
    val navController = rememberNavController()
    // Scaffold it is a layout that reserves specific spaces for specific tools
    // In this case it will set the Navigation Bar at the bottom of the screen
    Scaffold (
        bottomBar = {
            BottomNavigationBar(
                items = listOf(
                    BottomNavItem(
                        name = "Home",
                        route = "homescreen",
                        icon = Icons.Default.Home
                    ),
                    BottomNavItem(
                        name = "Chat",
                        route = "chatscreen",
                        icon = Icons.Default.Notifications
                    ),
                    BottomNavItem(
                        name = "Settings",
                        route = "settingscreen",
                        icon = Icons.Default.Settings,
                        badgeCount = 13
                    ),
                ),
                navController = navController,
                onItemClick = {
                    navController.navigate(it.route)
                }
            )
        }
    ) {paddingValues ->
        Spacer(modifier = Modifier.padding(paddingValues))  // I use this just to avoid the error
        BottomNavigationComponent(navController = navController)
    }
}

@Composable
fun BottomNavigationComponent(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = "homescreen"
    ) {
        composable ("homescreen") {
            HomeScreen()
        }
        composable ("chatscreen") {
            ChatScreen()
        }
        composable ("settingscreen") {
            SettingScreen()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomNavigationBar(
    items: List<BottomNavItem>,
    navController: NavController,
    modifier: Modifier = Modifier,
    onItemClick: (BottomNavItem) -> Unit
) {
    val backStackEntry = navController.currentBackStackEntryAsState()
    BottomNavigation(
        modifier = modifier,
        backgroundColor = Color.DarkGray,
        elevation = 5.dp
    ) {
        items.forEach { item ->
            val selected = item.route == backStackEntry.value?.destination?.route
            BottomNavigationItem(
                selected = selected,
                onClick = {
                    onItemClick(item)
                },
                selectedContentColor = Color.Green,
                unselectedContentColor = Color.Gray,
                icon = {
                    Column (
                        horizontalAlignment = CenterHorizontally
                    ) {
                        if(item.badgeCount > 0) {
                            BadgedBox (
                                badge = {
                                    Box(
                                        modifier = Modifier
                                            .wrapContentSize()
                                            .background(Color.Yellow)
                                    ) {
                                        Text(
                                            text = item.badgeCount.toString(),
                                            color = Color.Black
                                        )
                                    }
                                }
                            ) {
                                Icon(
                                    imageVector = item.icon,
                                    contentDescription = item.name
                                    )
                            }
                        } else {
                            Icon(
                                imageVector = item.icon,
                                contentDescription = item.name
                            )
                        }
                        if(selected) {
                            Text (
                                text = item.name,
                                textAlign = TextAlign.Center,
                                fontSize = 10.sp
                            )
                        }
                    }
                }
            )
        }
    }
}

@Composable
fun HomeScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "Home Screen here")
    }
}

@Composable
fun ChatScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "Chat Screen here")
    }
}

@Composable
fun SettingScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "Settings Screen here")
    }
}


@Composable
fun MultiSelectList() {
    var items by remember {
        mutableStateOf(
            (1..20).map {
                ListItem(
                    title = "Item $it",
                    isSelected = false
                )
            }
        )
    }
    // This column will load only the visible items on screen
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
    ) {
        items(items.size) {itemIndex ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        items = items.mapIndexed { clickIndex, item ->
                            if (clickIndex == itemIndex) {
                                item.copy(isSelected = !item.isSelected)
                            } else {
                                item
                            }
                        }
                    }
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = items[itemIndex].title
                )
                if(items[itemIndex].isSelected) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = "if selected item",
                        tint = Color.Green,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        }
    }
}


@Composable
fun CleanTheming () {
    Surface (
        color = MaterialTheme.colorScheme.background,
        modifier = Modifier
            .padding(
                LocalSpacing.current.large
            )
            .padding(
                spacing.large
            )
    ) {
        LocalSpacing.current.medium
    }
}


// This is actually the old navigation style
// For "easier" navigation watch: https://youtu.be/Q3iZyW2etm4?si=lhw3iYzimuzjvpwR
@Composable
fun EasierNavigation() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = "login"
    ) {
        composable("login") {
            LoginScreen(navController)
        }
        composable(
            route = "profile/¨{name}/{userId}/{timestamp}",
            arguments = listOf(
                navArgument("name") {
                    type = NavType.StringType
                },
                navArgument("userId") {
                    type = NavType.StringType
                },
                navArgument("timestamp") {
                    type = NavType.LongType
                }
            )
        ) {
            val name = it.arguments?.getString("name")!!
            val userId = it.arguments?.getString("userId")!!
            val timestamp = it.arguments?.getLong("timestamp")!!

            ProfileScreen(
                navController = navController,
                name = name,
                userId = userId,
                created = timestamp
            )
        }
        composable(
            route = "post/{showOnlyPostsByUser}",
            arguments = listOf(
                navArgument("showOnlyPostsByUser") {
                    type = NavType.BoolType
                    defaultValue = false
                }
            )
        ) {
            val showOnlyPostsByUser = it.arguments?.getBoolean("showOnlyPostsByUser") ?: false
            PostScreen(showOnlyPostsByUser)
        }
    }
}

@Composable
fun LoginScreen(
    navController: NavController
) {
    Column (
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Login Screen")
        Button(
            onClick = {
                navController.navigate("profile/roman/myUsername/2323")
            }
        ) {
            Text("Go to the Profile Screen")
        }
    }
}
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ProfileScreen(
    navController: NavController,
    name: String,
    userId: String,
    created: Long
) {
    val user = remember {
        User2(
            name = name,
            id = userId,
            created = LocalDateTime.ofInstant(
                Instant.ofEpochMilli(created), ZoneId.systemDefault()
            )
        )
    }
    Column (
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Profile Screen $user", textAlign = TextAlign.Center)
        Button(
            onClick = {
                navController.navigate("post/true")
            }
        ) {
            Text("Go to Post Screen")
        }
    }
}
@Composable
fun PostScreen(
    showOnlyPostsByUser: Boolean = false
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text("Post Screen, $showOnlyPostsByUser")
    }
}



@Composable
fun SupportAllScreens() {
    val windowInfo = rememberWindowInfo()
    if(windowInfo.screenWidthInfo is WindowInfo.WindowType.Compact) {
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            // List 1
            items(10) {
                Text(
                    text = "Item $it",
                    fontSize = 25.sp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.Cyan)
                        .padding(16.dp)
                )
            }
            // List 2
            items(10) {
                Text(
                    text = "Item $it",
                    fontSize = 25.sp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.Green)
                        .padding(16.dp)
                )
            }
        }
    }
    else {
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            LazyColumn(
                modifier = Modifier.weight(1f)
            ) {
                // List 1
                items(10) {
                    Text(
                        text = "Item $it",
                        fontSize = 25.sp,
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color.Cyan)
                            .padding(16.dp)
                    )
                }
            }
            LazyColumn(
                modifier = Modifier.weight(1f)
            ) {
                // List 2
                items(10) {
                    Text(
                        text = "Item $it",
                        fontSize = 25.sp,
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color.Green)
                            .padding(16.dp)
                    )
                }
            }
        }
    }
}


@Composable
fun AnimationMotionLayout () {
    Column {
        var progress by remember {
            mutableFloatStateOf(0f)
        }
        AnimatedProfileComponent(progress = progress)
        Spacer(modifier = Modifier.height(32.dp))
        Slider(
            value = progress,
            onValueChange = {
                progress = it
            },
            modifier = Modifier.padding(horizontal = 32.dp)
        )
    }
}

@OptIn(ExperimentalMotionApi::class)
@Composable
fun AnimatedProfileComponent(progress: Float) {
    val context = LocalContext.current
    val motionScene = remember {
        context.resources
            .openRawResource(R.raw.motion_scene)
            .readBytes()
            .decodeToString()
    }
    MotionLayout(
        motionScene = MotionScene(content = motionScene),
        progress = progress,
        modifier = Modifier.fillMaxWidth()
    ) {
        val properties = motionProperties(id = "profile_pic")
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.DarkGray)
                .layoutId("box")
        )
        Image(
            painter = painterResource(id = R.drawable.myflower),
            contentDescription = null,
            modifier = Modifier
                .clip(CircleShape)
                .border(
                    width = 2.dp,
                    color = properties.value.color("background"),
                    shape = CircleShape
                )
                .layoutId("profile_pic")
        )
        Text(
            text = "I am a Github Master",
            fontSize = 24.sp,
            modifier = Modifier.layoutId("username"),
            color = properties.value.color("background"),
        )
    }
}


@Composable
fun PaginationCompose() {
    // In case we change the orientation of the screen, the loaded items will be saved
    // because they are saved in the viewModel
    val viewModel = viewModel<MainViewModelPagination>()
    val state = viewModel.state
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
    ) {
        items(count = state.items.size) { index ->
            // Composable of each item
            val item = state.items[index]
            //Check if we are at the end of the list to load more items
            if(index >= state.items.size - 1 && !state.endReached && !state.isLoading) {
                viewModel.loadNextItems()
            }
            Column (
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(
                    text = item.title,
                    fontSize = 20.sp,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(item.description)
            }
        }
        item {
            // In the case we are loading items, we will display the loading icon
            if(state.isLoading) {
                Row (
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {
                    CircularProgressIndicator()
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BottomSheet() {
    val bottomSheetState = rememberBottomSheetState(    // How much does the sheet shows
        initialValue = BottomSheetValue.Collapsed,
        animationSpec = spring(
            dampingRatio = Spring.StiffnessHigh
        )
    )
    val scaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = bottomSheetState,
    )
    val scope = rememberCoroutineScope()
    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        sheetContent = {
            Box(modifier = Modifier
                .fillMaxWidth()
                .height(300.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Hidden text inside the bottom sheet",
                    fontSize = 60.sp
                )
            }
        },
        sheetBackgroundColor = Color.Green,
        sheetPeekHeight = 0.dp  // How much the bottom sheet show yourself at the beginning
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Button(
                onClick = {
                    scope.launch {
                        if(bottomSheetState.isCollapsed) {
                            bottomSheetState.expand()   // expand the bottom sheet
                        }
                        else {
                            bottomSheetState.collapse() // hide the bottom sheet
                        }
                    }

                }
            ) {
                Text(
                    text= "Toggle the bottom sheet, progress: ${bottomSheetState.progress}",
                    color = Color.White
                )
            }
        }
    }
}


@Composable
fun NavigationDrawer () {
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    Scaffold (
        scaffoldState = scaffoldState,
        topBar = {
             AppBar(
                 onNavigationIconClick = {
                     scope.launch {
                        if (scaffoldState.drawerState.isClosed) {
                            scaffoldState.drawerState.open()
                        }
                        else {
                            scaffoldState.drawerState.close()
                        }
                     }
                 }
             )
        },
        drawerGesturesEnabled = scaffoldState.drawerState.isOpen,   // only if the drawer is opened, we can drag it back
        drawerContent = {
            DrawerHeader()
            DrawerBody(
                items = listOf(
                    DrawerMenuItem(
                        id = "home",
                        title = "Home",
                        contentDescription = "Go to home screen",
                        icon = Icons.Default.Home
                    ),
                    DrawerMenuItem(
                        id = "settings",
                        title = "Settings",
                        contentDescription = "Go to settings screen",
                        icon = Icons.Default.Settings
                    ),
                    DrawerMenuItem(
                        id = "help",
                        title = "Help",
                        contentDescription = "Go to help screen",
                        icon = Icons.Default.Info
                    ),
                ),
                onItemClick = {
                    println("Clicked on ${it.title}")
                    // We would actually add navigation here like:
                    /*
                        when(it.id) {
                            "settings" -> navigateToSettingsScreen()
                        }
                    */
                }
            )
        }
    ) {

    }
}



