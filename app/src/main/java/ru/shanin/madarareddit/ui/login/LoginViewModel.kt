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
        savedStateHandle["validData"] = isUserNameValid(username) && isPasswordValid(password)
    }

    private fun isUserNameValid(username: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(username).matches()
    }

    private fun isPasswordValid(password: String): Boolean {
        return password.length > 7
    }
}
