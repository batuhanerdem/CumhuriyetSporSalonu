package com.example.cumhuriyetsporsalonu.domain.use_case

import com.example.cumhuriyetsporsalonu.data.remote.repository.FirebaseRepository
import com.example.cumhuriyetsporsalonu.domain.model.Lesson
import com.example.cumhuriyetsporsalonu.utils.Resource
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

@ViewModelScoped
class GetRequestableLessonsUseCase @Inject constructor(private val repository: FirebaseRepository) {
    fun execute(studentUid: String): Flow<Resource<List<Lesson>>> {
        return repository.getAllLessons().flatMapConcat { result ->
            if (result is Resource.Error) return@flatMapConcat flowOf(Resource.Error(result.message))
            result.data?.let { lessonList ->
                val requestableList = lessonList.filter {
                    it.studentUids.contains(studentUid).not() && it.requestUids.contains(
                        studentUid
                    ).not()
                }
                return@flatMapConcat flowOf(Resource.Success(requestableList))
            }
            flowOf(Resource.Error(result.message))
        }
    }
}