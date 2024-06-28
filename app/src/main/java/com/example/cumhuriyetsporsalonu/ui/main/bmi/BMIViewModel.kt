package com.example.cumhuriyetsporsalonu.ui.main.bmi

import com.example.cumhuriyetsporsalonu.data.remote.repository.FirebaseRepository
import com.example.cumhuriyetsporsalonu.ui.base.BaseViewModel
import com.example.cumhuriyetsporsalonu.utils.Resource
import com.example.cumhuriyetsporsalonu.utils.user.UserUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BMIViewModel @Inject constructor(
    private val firebaseRepository: FirebaseRepository
) : BaseViewModel<BMIActionBus>() {


    fun calculateBMI(weight: Double, height: Double): Double {
        return weight / (height * height)
    }

    fun calculateHealthyWeightRange(heightM: Double): Pair<Double, Double> {
        val minHeightInKg = 18.5 * heightM * heightM
        val maxHeightInKg = 24.9 * heightM * heightM
        return Pair(minHeightInKg, maxHeightInKg)
    }

    fun saveInfos(bmi: Double, height: Double, weight: Double) {
        val currentUser = UserUtils.getCurrentUser()
        currentUser ?: return
        val newUser = currentUser.copy(
            bmi = bmi.toString(), height = height.toString(), weight = weight.toString()
        )
        firebaseRepository.setUser(newUser) { action ->
            when (action) {
                is Resource.Error -> sendAction(BMIActionBus.ShowError())
                is Resource.Loading -> {}
                is Resource.Success -> {
                    UserUtils.setCurrentUser(newUser)
                    sendAction(BMIActionBus.BMISaved)
                }
            }

        }
    }

}