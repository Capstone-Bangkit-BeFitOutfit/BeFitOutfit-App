package com.bangkit.befitoutfit.helper

sealed class State<out T> private constructor() {
    data object Idle : State<Nothing>()
    data object Loading : State<Nothing>()
    data class Success<out T : Any>(val data: T) : State<T>()
    data class Error(val message: String) : State<Nothing>()
}
