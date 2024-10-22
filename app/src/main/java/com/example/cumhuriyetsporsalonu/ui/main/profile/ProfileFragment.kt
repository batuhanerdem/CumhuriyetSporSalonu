package com.example.cumhuriyetsporsalonu.ui.main.profile

import com.example.cumhuriyetsporsalonu.databinding.FragmentProfileBinding
import com.example.cumhuriyetsporsalonu.ui.base.BaseFragment
import com.example.cumhuriyetsporsalonu.ui.main.profile.adapter.LessonNameAdapter
import com.example.cumhuriyetsporsalonu.utils.user.UserUtils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : BaseFragment<ProfileActionBus, ProfileViewModel, FragmentProfileBinding>(
    FragmentProfileBinding::inflate, ProfileViewModel::class.java,
) {
    private lateinit var adapter: LessonNameAdapter

    override fun initPage() {
        setOnClickListeners()
        setUserProfile()
        setRV()
        viewModel.getLessons()
    }

    override suspend fun onAction(action: ProfileActionBus) {
        when (action) {
            is ProfileActionBus.ShowError -> {
                showErrorMessage(action.error)
            }

            ProfileActionBus.Init -> {}
            ProfileActionBus.LessonsLoaded -> {
                adapter.submitList(viewModel.lessonList)
            }
        }
    }

    private fun setUserProfile() {
        val user = UserUtils.getCurrentUser() ?: return
        binding.apply {
            "${user.name} ${user.surname}".also { tvName.text = it }
            tvHeightShow.text = user.height?.toDouble()?.toInt().toString() ?: "-"
            tvWeightShow.text = user.weight?.toDouble()?.toInt().toString() ?: "-"
            tvAgeShow.text = user.age ?: "-"
            tvBMIShow.text = user.bmi ?: "-"
        }
    }

    private fun setRV() {
        adapter = LessonNameAdapter()
        binding.rvLessonName.adapter = adapter
    }

    private fun setOnClickListeners() {
        binding.tvEditProfile.setOnClickListener {
            val action = ProfileFragmentDirections.actionProfileFragmentToEditProfileFragment()
            navigateTo(action)
        }
    }

}