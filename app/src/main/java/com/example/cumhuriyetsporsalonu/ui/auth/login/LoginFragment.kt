package com.example.cumhuriyetsporsalonu.ui.auth.login

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.cumhuriyetsporsalonu.databinding.FragmentLoginBinding
import com.example.cumhuriyetsporsalonu.ui.home.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding
    private val viewModel: LoginViewModel by viewModels()
    private val successString = "Basarili bir sekilde giris yapildi"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupOnClickListeners()
        setObservers()
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

    private fun setObservers() {
        viewModel.isLoggedInrSuccessful.observe(viewLifecycleOwner) {
            if (it) {
                showToast(successString)
                navigateHome()
            }
        }
        viewModel.errorMessage.observe(viewLifecycleOwner) {
            showToast(it)
        }
    }

    private fun navigateRegister() {
        val action = LoginFragmentDirections.actionLoginFragmentToRegisterFragment()
        findNavController().navigate(action)
    }

    private fun navigateHome() {
        Intent(requireContext(), MainActivity::class.java).apply {
            startActivity(this)
            requireActivity().finish()
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(
            requireContext(),
            message,
            Toast.LENGTH_SHORT
        ).show()
    }
}