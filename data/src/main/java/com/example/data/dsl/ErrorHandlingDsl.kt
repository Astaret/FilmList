package com.example.data.dsl

import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class ErrorHandlingDsl(private val scope: CoroutineScope) {
    private val tasks = mutableListOf<suspend () -> Unit>()

    fun task(action:suspend () -> Unit) {
        tasks.add(action)
    }

    suspend fun execute() {
        tasks.forEach {
            try {
                it
            }catch (
                e:Exception
            ){
                Log.d("Movie", "processWithAvoidingErrors: $e")
            }
        }
    }
}

fun CoroutineScope.errorHandled(actions:suspend ErrorHandlingDsl.()-> Unit){
    launch {
        ErrorHandlingDsl(this).apply { actions() }.execute()
    }
}
