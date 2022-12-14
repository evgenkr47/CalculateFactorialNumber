package com.example.factorialapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.math.BigInteger

class MainViewModel: ViewModel() {

    private val _state = MutableStateFlow<State?>(null)
    val state: StateFlow<State?> = _state.asStateFlow()


    fun calculate(value: String?){
        _state.value = Progress
        if (value.isNullOrBlank()){
            _state.value = Error
            return
        }

        viewModelScope.launch {
            val number = value.toLong()
            val result = factorial(number)
            _state.value = Factorial(result)
        }

    }


    private suspend fun factorial(number: Long): String{
        return withContext(Dispatchers.Default){
            var result = BigInteger.ONE
            for (i in 1..number){
                result = result.multiply(BigInteger.valueOf(i))
            }
            result.toString()
        }
    }


}