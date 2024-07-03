package com.example.cumhuriyetsporsalonu.ui.main.home

import android.util.Log
import androidx.core.view.isVisible
import com.example.cumhuriyetsporsalonu.databinding.FragmentHomeBinding
import com.example.cumhuriyetsporsalonu.ui.base.BaseFragment
import com.example.cumhuriyetsporsalonu.ui.main.home.adapter.LessonAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<HomeActionBus, HomeViewModel, FragmentHomeBinding>(
    FragmentHomeBinding::inflate, HomeViewModel::class.java
) {
    private lateinit var adapter: LessonAdapter
    override suspend fun onAction(action: HomeActionBus) {
        when (action) {
            HomeActionBus.Init -> {}
            is HomeActionBus.ShowError -> {
                showErrorMessage(action.errorMessage)
            }

            HomeActionBus.LessonsLoaded -> {
                if (viewModel.lessonList.isEmpty()) {
                    showNoLessonTV()
//                    return
                }
                adapter.submitList(viewModel.lessonList)
                Log.d(TAG, "onAction: ${binding.tvNoLessonFound.isVisible}")
            }
        }
    }

    override fun initPage() {
        viewModel.getUserInfo()
        setRV()
        viewModel.getLessonsByUid(viewModel.currentUser.uid)
    }

    private fun setRV() {
        adapter = LessonAdapter {
//nothing happens when lesson is clicked for now
        }
        binding.rvLesson.adapter = adapter
    }

    private fun showNoLessonTV() {
        binding.tvNoLessonFound.isVisible = true
    }

}