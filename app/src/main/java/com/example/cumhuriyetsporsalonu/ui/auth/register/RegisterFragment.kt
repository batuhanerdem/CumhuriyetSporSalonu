package com.example.cumhuriyetsporsalonu.ui.auth.register

import android.content.Intent
import com.example.cumhuriyetsporsalonu.R
import com.example.cumhuriyetsporsalonu.databinding.FragmentRegisterBinding
import com.example.cumhuriyetsporsalonu.ui.base.BaseFragment
import com.example.cumhuriyetsporsalonu.ui.main.MainActivity
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
            RegisterActionBus.RegisteredSuccessFully -> {
                progressBar.hide()
                val message = R.string.register_success.stringfy()
                showSuccessMessage(message)
                navigateHome()
            }

            is RegisterActionBus.UserIsNotRegistered -> {
                progressBar.hide()
                val message = R.string.register_error_default.stringfy()
                showErrorMessage(message)
            }
        }
    }

    private fun setupOnClickListeners() {
        binding.apply {
            btnRegister.setOnClickListener {
//                navigateHome()
//                return@setOnClickListener
                val email = edtEmail.text.toString()
                val password = edtPassword.text.toString()
                viewModel.createAccountWithEmailAndPassword(email, password)
            }
        }
    }

    private fun navigateHome() {
        Intent(requireContext(), MainActivity::class.java).apply {
            startActivity(this)
            requireActivity().finish()
        }
    }
}