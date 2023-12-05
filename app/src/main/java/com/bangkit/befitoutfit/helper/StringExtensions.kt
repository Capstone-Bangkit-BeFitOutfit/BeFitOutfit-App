package com.bangkit.befitoutfit.helper

import android.util.Patterns

object StringExtensions {
    private val LOWERCASE = Regex(".*[a-z].*")
    private val UPPERCASE = Regex(".*[A-Z].*")
    private val DIGIT = Regex(".*\\d.*")
    private val SPECIAL = Regex(".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>/?].*")

    fun String.emailChecker(): String = when {
        !Patterns.EMAIL_ADDRESS.matcher(this).matches() -> "Email is not valid"
        else -> ""
    }

    fun String.passwordChecker(isStrict: Boolean = false): String = when {
        this.length < 8 -> "Password must contain at least 8 characters"
        isStrict -> this.passwordCheckerStrict()
        else -> ""
    }

    private fun String.passwordCheckerStrict(): String = when {
        this.contains(LOWERCASE).not() -> "Password must contain at least 1 lowercase letter"
        this.contains(UPPERCASE).not() -> "Password must contain at least 1 uppercase letter"
        this.contains(DIGIT).not() -> "Password must contain at least 1 digit"
        this.contains(SPECIAL).not() -> "Password must contain at least 1 special character"
        else -> ""
    }

    fun String.errorMessageHandler(): String = when {
        this.contains("Unable to resolve host") -> "No internet connection"
        else -> "Error${if (this.isNotEmpty()) ": $this" else ""}"
    }
}
