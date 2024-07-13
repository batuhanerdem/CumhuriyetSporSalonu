package com.example.cumhuriyetsporsalonu.domain.use_case

import com.example.cumhuriyetsporsalonu.data.remote.repository.FirebaseRepository
import com.example.cumhuriyetsporsalonu.data.remote.repository.Uid
import com.example.cumhuriyetsporsalonu.domain.model.User
import com.example.cumhuriyetsporsalonu.utils.Resource
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@ViewModelScoped
class RegisterUseCase @Inject constructor(private val repository: FirebaseRepository) {
    suspend fun execute(
        email: String,
        password: String,
        name: String,
        surname: String,
    ): Flow<Resource<Uid>> = flow {
        repository.registerWithEmailPassword(email, password).collect { result ->
            when (result) {
                is Resource.Success -> {
                    result.data?.let {
                        val myUser = User(it, email, name, surname)
                        repository.setUser(myUser).collect { result ->
                            when (result) {
                                is Resource.Success -> emit(Resource.Success(it))
                                else -> emit(Resource.Error(message = result.message))
                            }
                        }
                    }
                }

                else -> emit(result)
            }
        }
    }

}