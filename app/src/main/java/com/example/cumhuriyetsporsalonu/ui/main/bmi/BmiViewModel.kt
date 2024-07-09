package com.example.cumhuriyetsporsalonu.ui.main.bmi

import com.example.cumhuriyetsporsalonu.data.remote.repository.FirebaseRepository
import com.example.cumhuriyetsporsalonu.ui.base.BaseViewModel
import com.example.cumhuriyetsporsalonu.utils.Resource
import com.example.cumhuriyetsporsalonu.utils.user.UserUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import java.math.BigDecimal
import java.math.RoundingMode
import javax.inject.Inject

@HiltViewModel
class BmiViewModel @Inject constructor(
    private val firebaseRepository: FirebaseRepository
) : BaseViewModel<BmiActionBus>() {


    fun calculateBMI(weight: Double, height: Double): Double {
        val heightAsMeter = height / 100
        val bmi = weight / (heightAsMeter * heightAsMeter)
        val bmiRounded = BigDecimal(bmi).setScale(1, RoundingMode.DOWN).toDouble()
        return bmiRounded
    }


    fun calculateHealthyWeightRange(height: Double): Pair<Double, Double> {
        val heightAsMeter = height / 100
        val minHeightInKg = 18.5 * heightAsMeter * heightAsMeter
        val maxHeightInKg = 24.9 * heightAsMeter * heightAsMeter

        val minHeightRounded = BigDecimal(minHeightInKg).setScale(1, RoundingMode.DOWN).toDouble()
        val maxHeightRounded = BigDecimal(maxHeightInKg).setScale(1, RoundingMode.DOWN).toDouble()

        return Pair(minHeightRounded, maxHeightRounded)
    }

    fun saveInfos(bmi: Double, height: Double, weight: Double) {
        val currentUser = UserUtils.getCurrentUser() ?: return
        val newUser = currentUser.copy(
            bmi = bmi.toString(), height = height.toString(), weight = weight.toString()
        )
        firebaseRepository.setUser(newUser) { action ->
            when (action) {
                is Resource.Error -> {
                    sendAction(BmiActionBus.ShowError())
                }

                is Resource.Loading -> {}
                is Resource.Success -> {
                    UserUtils.setCurrentUser(newUser)
                    sendAction(BmiActionBus.BmiSaved)
                }
            }
        }
    }
}