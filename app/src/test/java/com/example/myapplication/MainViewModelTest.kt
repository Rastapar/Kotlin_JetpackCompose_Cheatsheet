package com.example.myapplication

import androidx.lifecycle.viewmodel.compose.viewModel
import app.cash.turbine.test
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.hamcrest.MatcherAssert.assertThat

class MainViewModelTest{

    private lateinit var viewModel: MainViewModel

    @Before
    fun setUp(){
        viewModel = MainViewModel()
    }


}