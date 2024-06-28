package com.example.cumhuriyetsporsalonu.ui.main.bmi

import android.util.Log
import com.example.cumhuriyetsporsalonu.databinding.FragmentBmiBinding
import com.example.cumhuriyetsporsalonu.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BMIFragment : BaseFragment<BMIActionBus, BMIViewModel, FragmentBmiBinding>(
    FragmentBmiBinding::inflate, BMIViewModel::class.java
) {
    override suspend fun onAction(action: BMIActionBus) {
        when (action) {
            BMIActionBus.Init -> {}
            is BMIActionBus.ShowError -> {
                progressBar.hide()
            }

            BMIActionBus.BMICalculated -> {}
            BMIActionBus.BMISaved -> {}
        }
    }

    override fun initPage() {
        setOnClickListeners()
    }

    private fun setupRV() {

    }

    private fun setOnClickListeners() {
        binding.apply {
            btnCalculateBMI.setOnClickListener {
                try {
                    val height = edtHeight.text.toString().toDouble()
                    val weight = edtWeight.text.toString().toDouble()
                    val bmi = viewModel.calculateBMI(weight, height)
                    tvUserBMI.text = bmi.toString()

                    viewModel.saveInfos(bmi, height, weight)

                    val (minWeight, maxWeight) = viewModel.calculateHealthyWeightRange(height)
                    "$minWeight - $maxWeight".also { tvHealthyBMI.text = it }
                } catch (e: Exception) {
                
                }

            }
        }
    }

}