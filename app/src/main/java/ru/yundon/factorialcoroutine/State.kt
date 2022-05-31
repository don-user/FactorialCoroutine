package ru.yundon.factorialcoroutine

class State (
    val isError: Boolean = false,
    val isInProgress: Boolean = false,
    val factorial: String = ""
)