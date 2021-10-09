package ru.shanin.madarareddit.ui.login

import android.app.Application
import android.content.Intent
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.content.ContextCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import net.openid.appauth.AuthorizationException
import net.openid.appauth.AuthorizationService
import net.openid.appauth.TokenRequest
import ru.shanin.madarareddit.R
import ru.shanin.madarareddit.data.AuthRepository

class LoginViewModel(application: Application) : AndroidViewModel(application) {

    private val authRepository = AuthRepository()
    private val authService: AuthorizationService = AuthorizationService(getApplication())
    private val openAuthPageLiveEvent = Channel<Intent>(Channel.BUFFERED)
    private val toastLiveEvent = Channel<Int>(Channel.BUFFERED)
    private val loadingMutableLiveData = MutableStateFlow(false)
    private val authSuccessLiveEvent = Channel<Unit>(Channel.BUFFERED)

    val openAuthPageLiveData: Flow<Intent>
        get() = openAuthPageLiveEvent.receiveAsFlow()

    val loadingLiveData: StateFlow<Boolean>
        get() = loadingMutableLiveData

    val toastLiveData: Flow<Int>
        get() = toastLiveEvent.receiveAsFlow()

    val authSuccessLiveData: Flow<Unit>
        get() = authSuccessLiveEvent.receiveAsFlow()

    suspend fun onAuthCodeFailed(exception: AuthorizationException) {
        toastLiveEvent.send(R.string.auth_canceled)
    }

    fun onAuthCodeReceived(tokenRequest: TokenRequest) {
        loadingMutableLiveData.value = true
        authRepository.performTokenRequest(
            authService = authService,
            tokenRequest = tokenRequest,
            onComplete = {
                viewModelScope.launch {
                    loadingMutableLiveData.value = false
                    authSuccessLiveEvent.send(Unit)
                }
            },
            onError = {
                viewModelScope.launch {
                    loadingMutableLiveData.value = false
                    toastLiveEvent.send(R.string.auth_canceled)
                }
            }
        )
    }

    suspend fun openLoginPage() {
        val customTabsIntent = CustomTabsIntent.Builder()
            .setToolbarColor(ContextCompat.getColor(getApplication(), R.color.dark_blue))
            .build()

        val openAuthPageIntent = authService.getAuthorizationRequestIntent(
            authRepository.getAuthRequest(),
            customTabsIntent
        )

        openAuthPageLiveEvent.send(openAuthPageIntent)
    }

    override fun onCleared() {
        super.onCleared()
        authService.dispose()
    }
}
