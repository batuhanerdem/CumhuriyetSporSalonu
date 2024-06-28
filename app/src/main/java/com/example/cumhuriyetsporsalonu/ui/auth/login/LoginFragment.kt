package com.example.cumhuriyetsporsalonu.ui.auth.login

import com.example.cumhuriyetsporsalonu.R
import com.example.cumhuriyetsporsalonu.databinding.FragmentLoginBinding
import com.example.cumhuriyetsporsalonu.ui.auth.AuthActivity
import com.example.cumhuriyetsporsalonu.ui.base.BaseFragment
import com.example.cumhuriyetsporsalonu.utils.Stringfy.Companion.stringfy
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : BaseFragment<LoginActionBus, LoginViewModel, FragmentLoginBinding>(
    FragmentLoginBinding::inflate, LoginViewModel::class.java,
) {

    override fun initPage() {
        setupOnClickListeners()

    }

    override suspend fun onAction(action: LoginActionBus) {
        when (action) {
            LoginActionBus.Init -> {}
            is LoginActionBus.LoggedIn -> {
                progressBar.hide()
                val message = R.string.login_success.stringfy()
                showSuccessMessage(message)
                viewModel.userUid?.let {
                    setUidToActivityViewModel(it)
                    navigateSplash()
                }
            }

            LoginActionBus.Loading -> progressBar.show()
            is LoginActionBus.ShowError -> {
                progressBar.hide()
                val message = action.error ?: R.string.login_error_default.stringfy()
                showErrorMessage(message)
            }
        }
    }

    private fun setupOnClickListeners() {
        binding.apply {
            btnRegister.setOnClickListener {
                navigateRegister()
            }
            btnLogin.setOnClickListener {
                val email = edtEmail.text.toString()
                val password = edtPassword.text.toString()
                viewModel.loginWithEmailAndPassword(email, password)
            }
        }
    }

    private fun setUidToActivityViewModel(uid: String) {
        val activity = requireActivity() as AuthActivity
        activity.setUid(uid)
    }


    private fun navigateRegister() {
        val action = LoginFragmentDirections.actionLoginFragmentToRegisterFragment()
        navigateTo(action)
    }

    private fun navigateSplash() {
        val action = LoginFragmentDirections.actionLoginFragmentToSplashFragment()
        navigateTo(action)
    }

}