package com.example.cumhuriyetsporsalonu.ui.main.home

import com.example.cumhuriyetsporsalonu.databinding.FragmentHomeBinding
import com.example.cumhuriyetsporsalonu.ui.base.BaseFragment

class HomeFragment : BaseFragment<HomeActionBus, HomeViewModel, FragmentHomeBinding>(
    FragmentHomeBinding::inflate,
    HomeViewModel::class.java
) {
    override suspend fun onAction(action: HomeActionBus) {
        when (action) {
            HomeActionBus.Init -> {}
            HomeActionBus.Loading -> progressBar.show()
            is HomeActionBus.ShowErrorMessage -> {}
        }
    }

    override fun initPage() {
        //viewModel.startLoading()
        viewModel.getUserInfo()
        setUI()
    }

    private fun setUI() {
        binding.tvVerified.text = "verified: ${viewModel.currentUser.isVerified.toString()}"
    }

}