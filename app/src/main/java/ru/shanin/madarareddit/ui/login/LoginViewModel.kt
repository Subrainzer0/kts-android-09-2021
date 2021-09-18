package ru.shanin.madarareddit.ui.login

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel

class LoginViewModel(private val savedStateHandle: SavedStateHandle) : ViewModel() {

    val loginValidData: LiveData<Boolean> = savedStateHandle.getLiveData("validData")

    fun loginDataChanged(username: String, password: String) {
        if (username.isNotBlank() && password.isNotBlank()) {
            validateUser(username, password)
        }
        else {
            savedStateHandle["loginForm"] = false
        }
    }

    private fun validateUser(username: String, password: String) {
        when {
            !isUserNameValid(username) -> savedStateHandle["validData"] = false
            !isPasswordValid(password) -> savedStateHandle["validData"] = false
            else -> savedStateHandle["validData"] = true
        }
    }

    private fun isUserNameValid(username: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(username).matches()
    }

    private fun isPasswordValid(password: String): Boolean {
        return password.length > 7
    }
}
