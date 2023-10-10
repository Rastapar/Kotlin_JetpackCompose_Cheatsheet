package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myapplication.ui.theme.MyApplicationTheme

/*
    The Flows are all about being notified about changes in your code and sending them through a
    pipeline that potentially modifies them.
    A Flow at the end is a coroutine that can emit multiple values over a period of time.
    It is not necessary to use StateFlow with Compose because Compose already comes with a state
    If the screen changes to horizontal, the Activity will be recreated

    ShareFlow is used to send one time events. Not like StaveFlow that updates every time.
 */

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                }
                val viewModel = viewModel<MainViewModel>()
                val time = viewModel.countdown.collectAsState(initial = 10)

                val viewModel2 = viewModel<MainViewModel2>()
                val count = viewModel2.stateFlow.collectAsState(initial = 20)
                var square = 7

                val viewModel3 = viewModel<MainViewModel3>()

                // This block is used for catching SharedFlow events
                LaunchedEffect(key1 = true){
                    viewModel2.sharedFlow.collect{ num ->
                        square = num
                        println("FLOW COMPOSE: The square number is $square")
                    }
                }
                Box(modifier = Modifier.fillMaxSize()){
                    Text(text = "${time.value}", modifier = Modifier.align(Alignment.Center))   // Don't save state
                    Button(onClick = { viewModel2.incrementCounter() }) {       // Saves state
                        Text(text = "Counter ${count.value}")
                    }
                    Button(onClick = { viewModel2.squareNumber(square) },
                        modifier = Modifier.align(Alignment.TopEnd)) {       // Saves state
                        Text(text = "Counter ${square}")
                    }
                    Text(text = viewModel3.numberString)
                }
            }
        }
    }
}

