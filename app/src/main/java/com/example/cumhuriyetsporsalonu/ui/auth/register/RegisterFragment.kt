package com.example.cumhuriyetsporsalonu.ui.auth.register

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.cumhuriyetsporsalonu.databinding.FragmentRegisterBinding
import com.example.cumhuriyetsporsalonu.ui.home.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFragment : Fragment() {
    private lateinit var binding: FragmentRegisterBinding
    private val viewModel: RegisterViewModel by viewModels()
    private val successString = "Basarili bir sekilde kayit olundu"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegisterBinding.inflate(inflater)
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
                navigateHome()
                return@setOnClickListener
                val email = edtEmail.text.toString()
                val password = edtPassword.text.toString()
                viewModel.createAccountWithEmailAndPassword(email, password)
            }
        }
    }

    private fun setObservers() {
        viewModel.isRegisterSuccessful.observe(viewLifecycleOwner) {
            if (it) {
                showToast(successString)
                navigateHome()
            }
        }
        viewModel.errorMessage.observe(viewLifecycleOwner) {
            showToast(it)
        }
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