package com.example.jetpackcompose

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

// The navigation will navigate among Composables where each Composable is a screen
@Composable
fun Navigation () {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Screen.MainScreen.route
    ) {
        // first screen
        composable(route = Screen.MainScreen.route) {
            MainScreen(navController = navController)
        }
        composable(
            // this is a mandatory argument, optional would be "/?somename={somename}"
            // also in case of an optional arguments, the appends of the Screen file must be modified too
            route = Screen.DetailScreen.route + "/{somename}",

            // This must have a defaultValue set, or have nullable = true (which implicitly sets the default value to null)
            arguments = listOf(
                navArgument("somename") {
                    type = NavType.StringType
                    defaultValue = "Roman"
                }
            )
        ) { entry ->
            DetailScreen(
                name = entry.arguments?.getString("somename")
            )
        }
    }
}

@Composable
fun MainScreen(navController: NavController) {
    var text by remember {
        mutableStateOf("")
    }
    Column(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 50.dp)
    ) {
        TextField(
            value = text,
            onValueChange = {
                text = it
            },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = {
                navController.navigate(Screen.DetailScreen.withArgs(text))
            },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text(text = "Press me to go to the Detailed Screen")
        }
    }
}

@Composable
fun DetailScreen(name: String?) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "Hello, $name.\nI am the next screen")
    }
}