package com.example.cumhuriyetsporsalonu.domain.use_case

import com.example.cumhuriyetsporsalonu.data.remote.repository.FirebaseRepository
import com.example.cumhuriyetsporsalonu.data.remote.repository.Uid
import com.example.cumhuriyetsporsalonu.domain.model.User
import com.example.cumhuriyetsporsalonu.utils.Resource
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.zip
import javax.inject.Inject

@ViewModelScoped
class RegisterUseCase @Inject constructor(private val repository: FirebaseRepository) {
    fun execute(
        email: String,
        password: String,
        name: String,
        surname: String,
    ): Flow<Resource<Uid>> {
        return repository.register(email, password).flatMapConcat { result ->
            if (result is Resource.Error) return@flatMapConcat flowOf(Resource.Error(result.message))

            result.data ?: return@flatMapConcat flowOf(Resource.Error(result.message))
            val myUser = User(result.data, email, name, surname)

            //needed to pass uid inside the flow but setUser is not returning any uid,
            //this is gonna be fixed after setUser return type changed.
            repository.setUser(myUser).zip(flowOf(Resource.Success(result.data))) { setUser, uid ->
                if (setUser is Resource.Error) Resource.Error<Uid>(setUser.message)
                uid.data?.let {
                    return@zip Resource.Success(it)
                }
                Resource.Error(uid.message)
            }

        }
    }

}


