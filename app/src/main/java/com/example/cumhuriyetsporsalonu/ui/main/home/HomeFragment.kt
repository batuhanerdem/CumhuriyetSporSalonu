package com.example.cumhuriyetsporsalonu.ui.main.home

import androidx.core.view.isVisible
import com.example.cumhuriyetsporsalonu.databinding.FragmentHomeBinding
import com.example.cumhuriyetsporsalonu.ui.base.BaseFragment
import com.example.cumhuriyetsporsalonu.ui.main.home.adapter.LessonListingAdapter
import com.example.cumhuriyetsporsalonu.utils.SelectableData.Companion.toSelectable
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<HomeActionBus, HomeViewModel, FragmentHomeBinding>(
    FragmentHomeBinding::inflate, HomeViewModel::class.java
) {
    private lateinit var adapter: LessonListingAdapter
    override suspend fun onAction(action: HomeActionBus) {
        when (action) {
            HomeActionBus.Init -> {}
            is HomeActionBus.ShowError -> {
                showErrorMessage(action.errorMessage)
            }

            HomeActionBus.LessonsLoaded -> {
                submitListOrShowNoLesson()
            }
        }
    }

    override fun initPage() {
        setRV()
        setOnClickListeners()
        viewModel.getLessonsByUid()
    }

    private fun setRV() {
        adapter = LessonListingAdapter {
//nothing happens when lesson is clicked for now
        }
        binding.rvLesson.adapter = adapter
    }

    private fun setOnClickListeners() {
        binding.imgPlus.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToRequestLessonFragment()
            navigateTo(action)
        }
    }

    private fun showNoLessonTV(isListEmpty: Boolean) {
        binding.tvNoLessonFound.isVisible = isListEmpty
    }

    private fun submitListOrShowNoLesson() {
        adapter.submitList(viewModel.lessonList.toMutableList().toSelectable())
        showNoLessonTV(viewModel.lessonList.isEmpty())
    }

}