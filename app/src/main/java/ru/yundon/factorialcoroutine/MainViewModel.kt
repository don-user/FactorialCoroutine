package ru.yundon.factorialcoroutine

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.math.BigInteger
import kotlin.concurrent.thread
import kotlin.coroutines.suspendCoroutine

class MainViewModel: ViewModel() {

    private val _state = MutableLiveData<State>()
    val state: LiveData<State>
        get() = _state

    fun calculate(value: String?){

        _state.value = Progress
        if (value.isNullOrBlank()){
            _state.value = Error
            return
        }
        viewModelScope.launch {
            val number = value.toLong()
            val res = factorial(number)
            _state.value = FactorialResult(res)
        }
    }

    //реализация с использование многопоточность корутин (withContext)
    //BigInteger - очень большие цифры, рабта с ними отличается от работы с Int
    private suspend fun factorial(number: Long) : String {
        return withContext(Dispatchers.Default){
            var result = BigInteger.ONE
            for (i in 1..number) {
                result = result.multiply(BigInteger.valueOf(i))
            }
            result.toString()
        }
    }

    //suspendCoroutine используется когда реализованн коллбэк (асинхронный поток без корутин) с использование thread
//    private suspend fun factorial(number: Long) : String{
//        return suspendCoroutine {
//            thread {
//                var result = BigInteger.ONE
//                for (i in 1..number){
//                    result = result.multiply(BigInteger.valueOf(i))
//                }
//                it.resumeWith(Result.success(result.toString()))
//            }
//        }
//    }
}