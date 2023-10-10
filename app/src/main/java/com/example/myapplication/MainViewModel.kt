package com.example.myapplication

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.asFlow
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

class MainViewModel : ViewModel() {
    val countdown = flow<Int> {
        val startingValue = 10
        var currentValue = startingValue
        emit(currentValue)  // Notifying the UI about the change
        println("Emit enviado")

        while (currentValue > 0){
            delay(1000)
            currentValue--
            emit(currentValue)  // Notifying the UI about the change
            println("Emit enviado")
        }
        println("Countdown finished")
    }

    init {
        //collectFlow()
        //flowCollectorSimpleOperators()
        //flowCollectorTerminalOperators()
        //flatteningFlow()
        bufferFlow()
    }

    // FLOW BASICS CHAPTER (1)
    // Not very good practice, good only for simple Collect Flows like this one
    // Using 'collectLatest' instead of 'collect' will get only the latest messages
    // So if there is some delay or something similar, some messages may not appear with 'collectLatest'
    private fun collectFlow(){
        viewModelScope.launch {
            countdown.collect { time ->
                println("The current time in our app is $time")
            }
        }
    }

    // FLOW OPERATORS CHAPTER (2)
    // This countdown call goes apart from the 'MainActivity',
    // it means that this countdown starts and on the other side the one in the 'MainActivity'
    private fun flowCollectorSimpleOperators(){
        viewModelScope.launch {
            countdown
                .filter { time ->   // Filter and return only even numbers
                    time % 2 == 0
                }
                .map { time ->      // Apply for each number the expression, it returns the result of the given expression
                    time * 3
                }
                .onEach { time ->   // Apply the expression for each number without return
                    println("After filters the time is: $time")
                }
                .collect { time ->  // Get the values of the Flow
                    println("The current time in our app is $time")
                }
        }
    }

    // Terminal operators because they wait the Flow to end and do something with the information
    private fun flowCollectorTerminalOperators(){
        viewModelScope.launch {
            val count = countdown
                .count { time ->        // this operator will count the values that match the expression
                    time % 3 == 0
                }
            println("The count of the values: is $count")

            val sumValues = countdown
                .reduce{ accumulator, value ->      // applies some expression to the values and saves the result in the accumulator
                    accumulator + value
                }
            println("The accumulation of the values is: $sumValues")

            val sumValuesWithInitValue = countdown
                .fold (100) { accumulator, value ->      // The same that 'reduce' but with initial value in 'accumulator'
                    accumulator + value
                }
            println("The accumulation of the values is: $sumValuesWithInitValue")
        }
    }

    // flattening
    // 'flatMapConcat' returns another Flow, that is a concatenation of the actual flow plus the given one
    // 'flatMapMerge' (not very recommended) does the same that 'flatMapConcat' but it doesn't wait to get all the data,
    // once it has some value (like from the list), it starts applying the function (inner Flow)
    // 'flatMapLatest' will consider only the latest one value
    private fun flatteningFlow(){
        val flow1 = flow {
            emit(1)
            delay(500)
            emit(2)
        }
        viewModelScope.launch {
            flow1
                .flatMapConcat { num ->
                    flow {
                        emit(num + 1)
                        delay(500)
                        emit(num + 2)
                    }
                }
                .collect {
                    println("The flattening Flow value is: $it")
                }
        }

        // This flattening prints: 4 5 6 ... 8
        // So if there is no pause, it will print all, but if there is pause then only the last one
        val flow2 = (1..3).asFlow()
        viewModelScope.launch {
            flow2
                .flatMapLatest { num ->
                    flow {
                        emit(num + 3)
                        delay(500)
                        emit(num + 5)
                    }
                }
                .collect {
                    println("The flattening Latest Flow value is: $it")
                }
        }
    }

    // When the Flow is launched without 'buffer', each 'emit' is processed in the coroutine until
    // it is finished. And then the next emit will continue (delay first between emits).
    // With 'buffer', the 'emits' in the Flow won't wait until the previous 'emit' has finished'
    // With 'conflate' instead of 'buffer', if while we are eating the salat
    // there are new deliveries (lunch, dessert), we will get the last one once we have finished the salat.
    // So the lunch will be delivered, but never eaten nor finished.
    // With 'conflate' and 'collectLatest' we won't wait to finish the dish if the new dish has arrived.
    // So we will change to the next dish even if the previous one hasn't been finished.
    private fun bufferFlow(){
        val flow = flow {
            delay(200)
            emit("Salat")
            delay(500)
            emit("Lunch")
            delay(300)
            emit("Dessert")
        }
        viewModelScope.launch {
            flow
                .onEach {
                    println("FLOW: Delivered $it")
                }
                .conflate()
                .collect {
                    println("FLOW: Eating $it")
                    delay(1000)
                    println("FLOW: Finished $it")
                }
        }
    }
}