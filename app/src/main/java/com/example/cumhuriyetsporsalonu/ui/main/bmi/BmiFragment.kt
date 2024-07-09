package com.example.cumhuriyetsporsalonu.ui.main.bmi

import com.example.cumhuriyetsporsalonu.databinding.FragmentBmiBinding
import com.example.cumhuriyetsporsalonu.ui.base.BaseFragment
import com.example.cumhuriyetsporsalonu.utils.NullOrEmptyValidator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BmiFragment : BaseFragment<BmiActionBus, BmiViewModel, FragmentBmiBinding>(
    FragmentBmiBinding::inflate, BmiViewModel::class.java
) {
    override suspend fun onAction(action: BmiActionBus) {
        when (action) {
            BmiActionBus.Init -> {}
            is BmiActionBus.ShowError -> {}
            // no need to show anything about bmi saving
            BmiActionBus.BmiSaved -> {}
        }
    }

    override fun initPage() {
        setOnClickListeners()
    }


    override fun onResume() {
        super.onResume()
        clearFields()
    }

    private fun setOnClickListeners() {
        binding.apply {
            btnCalculateBMI.setOnClickListener {
                val isValidated = NullOrEmptyValidator.validate(edtHeight.text, edtWeight.text)
                if (!isValidated) return@setOnClickListener
                val height = edtHeight.text.toString().toDouble()
                val weight = edtWeight.text.toString().toDouble()
                val bmi = viewModel.calculateBMI(weight, height)
                tvUserBMI.text = bmi.toString()
                viewModel.saveInfos(bmi, height, weight)

                val (minWeight, maxWeight) = viewModel.calculateHealthyWeightRange(height)
                "$minWeight - $maxWeight".also { tvHealthyBMI.text = it }

            }
        }
    }

    private fun clearFields() {
        binding.edtHeight.text.clear()
        binding.edtWeight.text.clear()
    }

}