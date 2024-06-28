package com.example.cumhuriyetsporsalonu.ui.main.profile.edit_profile

import com.example.cumhuriyetsporsalonu.data.remote.repository.FirebaseRepository
import com.example.cumhuriyetsporsalonu.domain.model.User
import com.example.cumhuriyetsporsalonu.ui.base.BaseViewModel
import com.example.cumhuriyetsporsalonu.utils.Resource
import com.example.cumhuriyetsporsalonu.utils.user.UserUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class EditProfileViewModel @Inject constructor(
    private val firebaseRepository: FirebaseRepository
) : BaseViewModel<EditProfileActionBus>() {
    val currentUser: User? get() = UserUtils.getCurrentUser()

    fun saveUser(
        name: String?, surname: String?, age: String?, height: String?, weight: String?
    ) {
        val user = currentUser ?: return
        val newUser = user.copy(name = name, surname = surname, age = age, height =  height, weight =  weight)
        firebaseRepository.setUser(newUser) { action ->
            when (action) {
                is Resource.Error -> sendAction(EditProfileActionBus.ShowError(action.message))
                is Resource.Loading -> {}
                is Resource.Success -> {
                    sendAction(EditProfileActionBus.UserUpdated)
                    UserUtils.setCurrentUser(user)
                }
            }
        }
    }
}
