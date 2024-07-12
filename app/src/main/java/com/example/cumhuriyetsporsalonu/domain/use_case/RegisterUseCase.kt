package com.example.cumhuriyetsporsalonu.domain.use_case

import com.example.cumhuriyetsporsalonu.data.remote.repository.FirebaseRepository
import com.example.cumhuriyetsporsalonu.data.remote.repository.Uid
import com.example.cumhuriyetsporsalonu.domain.model.User
import com.example.cumhuriyetsporsalonu.utils.Resource
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class RegisterUseCase @Inject constructor(private val repository: FirebaseRepository) {
    fun execute(
        email: String,
        password: String,
        name: String,
        surname: String,
        callBack: (Resource<out Uid>) -> Unit
    ) {
        repository.registerWithEmailPassword(email, password) { result ->
            when (result) {
                is Resource.Success -> {
                    result.data?.let {
                        val myUser = User(it, email, name, surname)
                        repository.setUser(myUser) { result ->
                            when (result) {
                                is Resource.Success -> callBack(Resource.Success(it))
                                else -> callBack(result)
                            }
                        }
                    }
                }

                else -> callBack(result)
            }

        }

    }

}