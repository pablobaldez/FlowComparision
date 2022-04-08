package com.example.myapplication

import kotlinx.coroutines.delay

suspend fun Int.isEven(): String {
    delay(1000)
    return "$this is even ${this % 2 == 0}"
}