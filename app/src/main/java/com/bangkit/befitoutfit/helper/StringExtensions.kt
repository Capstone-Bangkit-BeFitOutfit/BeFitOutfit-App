package com.bangkit.befitoutfit.helper

object StringExtensions {
    fun String.errorMessageHandler(): String = when {
        this.contains("Unable to resolve host") -> "No internet connection"
        else -> "Error${if (this.isNotEmpty()) ": $this" else ""}"
    }
}
