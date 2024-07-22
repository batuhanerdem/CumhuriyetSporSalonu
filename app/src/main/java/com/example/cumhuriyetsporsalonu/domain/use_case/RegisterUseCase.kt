package com.example.cumhuriyetsporsalonu.domain.use_case

import android.util.Log
import com.example.cumhuriyetsporsalonu.data.remote.repository.FirebaseRepository
import com.example.cumhuriyetsporsalonu.data.remote.repository.Uid
import com.example.cumhuriyetsporsalonu.domain.model.User
import com.example.cumhuriyetsporsalonu.utils.Resource
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

@ViewModelScoped
class RegisterUseCase @Inject constructor(private val repository: FirebaseRepository) {
    suspend fun execute(
        email: String,
        password: String,
        name: String,
        surname: String,
    ): Flow<Resource<Uid>> = flow {
        repository.register(email, password).flatMapConcat { result ->
            if (result !is Resource.Success) return@flatMapConcat flow { emit(result) }
            result.data ?: return@flatMapConcat flow { }
            emit(Resource.Success(result.data))
            val myUser = User(result.data, email, name, surname)
            repository.setUser(myUser)
        }.collect {
            Log.d("tag", "execute: $it ${it.data} ${it.message}")
            if (it is Resource.Error) emit(Resource.Error(it.message))
        }
    }

}


