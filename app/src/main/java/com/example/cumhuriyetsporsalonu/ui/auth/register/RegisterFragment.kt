package com.example.cumhuriyetsporsalonu.ui.auth.register

import com.example.cumhuriyetsporsalonu.R
import com.example.cumhuriyetsporsalonu.databinding.FragmentRegisterBinding
import com.example.cumhuriyetsporsalonu.ui.auth.AuthActivity
import com.example.cumhuriyetsporsalonu.ui.base.BaseFragment
import com.example.cumhuriyetsporsalonu.utils.Stringfy.Companion.stringfy
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFragment :
    BaseFragment<RegisterActionBus, RegisterViewModel, FragmentRegisterBinding>(
        FragmentRegisterBinding::inflate, RegisterViewModel::class.java
    ) {

    override fun initPage() {
        setupOnClickListeners()
    }

    override suspend fun onAction(action: RegisterActionBus) {
        when (action) {
            RegisterActionBus.Init -> {}
            RegisterActionBus.Loading -> progressBar.show()
            RegisterActionBus.RegisteredSuccessfully -> {
                progressBar.hide()
                setUidToActivityViewModel(viewModel.userUid!!) //look here sometime
                val message = R.string.register_success.stringfy()
                showSuccessMessage(message)
                navigateSplash()
            }

            is RegisterActionBus.ShowError -> {
                progressBar.hide()
                val message = action.error ?: R.string.register_error_default.stringfy()
                showErrorMessage(message)
            }
        }
    }

    private fun setupOnClickListeners() {
        binding.apply {
            btnRegister.setOnClickListener {
                val email = edtEmail.text.toString()
                val password = edtPassword.text.toString()
                val name = edtName.text.toString()
                val surname = edtSurname.text.toString()
                viewModel.createAccountWithEmailPasswordName(email, password, name, surname)
            }
            tvLogin.setOnClickListener {
                navigateBack()
            }
        }
    }

    private fun setUidToActivityViewModel(uid: String) {
        val activity = requireActivity() as AuthActivity
        activity.setUid(uid)
    }

    private fun navigateSplash() {
        val action = RegisterFragmentDirections.actionRegisterFragmentToSplashFragment()
        navigateTo(action)
    }

}