package com.example.cumhuriyetsporsalonu.domain.use_case

import com.example.cumhuriyetsporsalonu.data.remote.repository.FirebaseRepository
import com.example.cumhuriyetsporsalonu.domain.model.Lesson
import com.example.cumhuriyetsporsalonu.utils.Resource
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

@ViewModelScoped
class GetRequestableLessonsUseCase @Inject constructor(private val repository: FirebaseRepository) {
    suspend fun execute(studentUid: String): Flow<Resource<List<Lesson>>> = flow {
        repository.getAllLessons().collect { result ->
            if (result !is Resource.Success) return@collect emit(result)
            result.data?.let { lessonList ->
                val requestableList = lessonList.filter {
                    it.studentUids.contains(studentUid).not() && it.requestUids.contains(
                        studentUid
                    ).not()
                }
                emit(Resource.Success(requestableList))
            }
        }
    }
}