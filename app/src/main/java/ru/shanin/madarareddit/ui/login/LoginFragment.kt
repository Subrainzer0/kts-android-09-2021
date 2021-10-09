package ru.shanin.madarareddit.ui.login

import android.content.Intent
import android.os.Bundle
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import kotlinx.coroutines.flow.collect
import net.openid.appauth.AuthorizationException
import net.openid.appauth.AuthorizationResponse
import ru.shanin.madarareddit.R
import ru.shanin.madarareddit.databinding.FragmentLoginBinding
import ru.shanin.madarareddit.utils.extensions.launchOnStartedState
import ru.shanin.madarareddit.utils.extensions.toast

class LoginFragment : Fragment(R.layout.fragment_login) {

    private val loginViewModel: LoginViewModel by viewModels()

    private val binding: FragmentLoginBinding by viewBinding()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        bindViewModel()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == AUTH_REQUEST_CODE && data != null) {
            val tokenExchangeRequest = AuthorizationResponse.fromIntent(data)
                ?.createTokenExchangeRequest()
            val exception = AuthorizationException.fromIntent(data)
            when {
                tokenExchangeRequest != null && exception == null ->
                    loginViewModel.onAuthCodeReceived(tokenExchangeRequest)
                exception != null -> {
                    viewLifecycleOwner.launchOnStartedState {
                        loginViewModel.onAuthCodeFailed(exception)
                    }
                }
            }
        }
        else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    private fun bindViewModel() {
        binding.login.setOnClickListener {
            viewLifecycleOwner.launchOnStartedState {
                loginViewModel.openLoginPage()
            }
        }
        viewLifecycleOwner.launchOnStartedState {
            loginViewModel.loadingLiveData.collect { updateIsLoading(it) }
        }
        viewLifecycleOwner.launchOnStartedState {
            loginViewModel.openAuthPageLiveData.collect { openAuthPage(it) }
        }
        viewLifecycleOwner.launchOnStartedState {
            loginViewModel.toastLiveData.collect { toast(it) }
        }
        viewLifecycleOwner.launchOnStartedState {
            loginViewModel.authSuccessLiveData.collect {
                findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToMainFragment())
            }
        }
    }

    private fun updateIsLoading(isLoading: Boolean) = with(binding) {
        login.isVisible = !isLoading
    }

    private fun openAuthPage(intent: Intent) {
        startActivityForResult(intent, AUTH_REQUEST_CODE)
    }

    companion object {
        private const val AUTH_REQUEST_CODE = 342
    }
}
