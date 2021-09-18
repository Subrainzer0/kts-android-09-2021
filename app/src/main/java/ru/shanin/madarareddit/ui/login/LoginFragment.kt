package ru.shanin.madarareddit.ui.login

import android.os.Bundle
import android.text.InputFilter
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.EditText
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

        val usernameEditText = binding.username

        val passwordEditText = binding.password

        val loginButton = binding.login

        loginViewModel.loginValidData.observe(viewLifecycleOwner,
            Observer { loginFormState ->
                if (loginFormState == null) {
                    return@Observer
                }
                loginButton.isEnabled = loginFormState
            }
        )

        setUsernameTextChangedListener(usernameEditText, passwordEditText)

        setPasswordTextChangedListener(usernameEditText, passwordEditText)

        setPasswordEditorActionListener(passwordEditText)

        setFilterForWhiteSpace(passwordEditText)

        setOnButtonClickListener(loginButton)
    }

    private fun setUsernameTextChangedListener(usernameEditText: EditText, passwordEditText: EditText) {
        usernameEditText.doAfterTextChanged { text ->
            val username = text.toString().trim()

            val password = passwordEditText.text.toString().trim()
            loginViewModel.loginDataChanged(username, password)
        }
    }

    private fun setPasswordTextChangedListener(usernameEditText: EditText, passwordEditText: EditText) {
        passwordEditText.doAfterTextChanged { editable ->
            val username = usernameEditText.text.toString().trim()

            val password = editable.toString().trim()
            loginViewModel.loginDataChanged(username, password)
        }
    }

    private fun setPasswordEditorActionListener(passwordEditText: EditText) {
        passwordEditText.setOnEditorActionListener { _, actionId, _ ->

            if (actionId == EditorInfo.IME_ACTION_DONE) {
                activity?.window?.hideKeyboard()
            }
            false
        }
    }

    private fun setFilterForWhiteSpace(editText: EditText) {
        editText.filters = arrayOf(InputFilter { source, _, _, _, _, _ ->
            source.toString().filterNot { it.isWhitespace() }
        })
    }

    private fun setOnButtonClickListener(button: Button) {
        button.setOnClickListener {
            val action = LoginFragmentDirections.actionLoginFragmentToMainFragment()
            findNavController().navigate(action)
        }
    }
}
