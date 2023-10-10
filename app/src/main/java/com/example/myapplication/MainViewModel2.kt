package com.example.myapplication

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.fold
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.reduce
import kotlinx.coroutines.launch

/*
    Undescore means that the values is Mutable.
    The StateFlow get the updates of the Activity, its state.

*/

class MainViewModel2 : ViewModel() {

    val _stateFlow = MutableStateFlow(0)    // The value is like the id of the states inside _stateFlow
    val stateFlow = _stateFlow.asStateFlow()    // This State is immutable

    val _sharedFlow = MutableSharedFlow<Int>(replay = 4)
    val sharedFlow = _sharedFlow.asSharedFlow()

    // It is necessary to launch another coroutine because after the SharedFlow 'collect',
    // the viewModelScope will end
    // Important, first the collectors must be declared and then the event 'squareNumber'
    // In case we want to catch more than one event, we would set the parameter 'replay' in the SharedFlow collector
    init {
        viewModelScope.launch {
            sharedFlow.collect {
                delay(2000)
                println("FIRST SHARED FLOW: The received number is $it")
            }
        }
        viewModelScope.launch {
            sharedFlow.collect {
                delay(3000)
                println("SECOND SHARED FLOW: The received number is $it")
            }
        }
        squareNumber(3)
        squareNumber(5)
    }

    fun incrementCounter(){
        _stateFlow.value += 1
    }

    // shared flow has to be in a coroutine or suspend function
    fun squareNumber(num : Int) {
        viewModelScope.launch {
            _sharedFlow.emit(num * num)
        }
    }
}