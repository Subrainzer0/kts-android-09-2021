package ru.shanin.madarareddit.ui.login

import android.os.Bundle
import android.text.InputFilter
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import ru.shanin.madarareddit.R
import ru.shanin.madarareddit.databinding.FragmentLoginBinding
import ru.shanin.madarareddit.utils.extensions.hideKeyboard

class LoginFragment : Fragment(R.layout.fragment_login) {

    private val loginViewModel: LoginViewModel by viewModels()

    private val binding: FragmentLoginBinding by viewBinding()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loginViewModel.loginValidData.observe(viewLifecycleOwner,
            Observer { loginFormState ->
                if (loginFormState == null) {
                    return@Observer
                }

                binding.login.isEnabled = loginFormState
            }
        )

        setUsernameTextChangedListener()
        setPasswordTextChangedListener()
        setPasswordEditorActionListener()
        setFilterForWhiteSpace()
        setOnButtonClickListener()
    }

    private fun setUsernameTextChangedListener() = with(binding) {
        username.doAfterTextChanged { text ->
            val username = text.toString().trim()

            val password = password.text.toString().trim()
            loginViewModel.loginDataChanged(username, password)
        }
    }

    private fun setPasswordTextChangedListener() = with(binding) {
        password.doAfterTextChanged { text ->
            val password = text.toString().trim()

            val username = username.text.toString().trim()
            loginViewModel.loginDataChanged(username, password)
        }
    }

    private fun setPasswordEditorActionListener() = with(binding) {
        password.setOnEditorActionListener { _, actionId, _ ->

            if (actionId == EditorInfo.IME_ACTION_DONE) {
                activity?.window?.hideKeyboard()
            }
            false
        }
    }

    private fun setFilterForWhiteSpace() = with(binding) {
        password.filters = arrayOf(InputFilter { source, _, _, _, _, _ ->
            source.toString().filterNot { it.isWhitespace() }
        })
    }

    private fun setOnButtonClickListener() = with(binding) {
        login.setOnClickListener {
            val action = LoginFragmentDirections.actionLoginFragmentToMainFragment()
            findNavController().navigate(action)
        }
    }
}
