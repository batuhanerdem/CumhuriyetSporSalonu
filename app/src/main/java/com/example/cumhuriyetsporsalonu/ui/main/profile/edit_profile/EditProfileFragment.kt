package com.example.cumhuriyetsporsalonu.ui.main.profile.edit_profile

import com.example.cumhuriyetsporsalonu.R
import com.example.cumhuriyetsporsalonu.databinding.FragmentEditProfileBinding
import com.example.cumhuriyetsporsalonu.ui.base.BaseFragment
import com.example.cumhuriyetsporsalonu.utils.Stringfy.Companion.stringfy
import com.example.cumhuriyetsporsalonu.utils.user.UserUtils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditProfileFragment :
    BaseFragment<EditProfileActionBus, EditProfileViewModel, FragmentEditProfileBinding>(
        FragmentEditProfileBinding::inflate, EditProfileViewModel::class.java,
    ) {

    override fun initPage() {
        setOnClickListeners()
        setEditTexts()
    }

    override suspend fun onAction(action: EditProfileActionBus) {
        when (action) {
            is EditProfileActionBus.ShowError -> {
                showErrorMessage(action.error)
            }

            EditProfileActionBus.Init -> {}
            EditProfileActionBus.UserUpdated -> {
                clearFields()
                showSuccessMessage(R.string.update_success.stringfy())
            }
        }
    }

    private fun setEditTexts() {
        val currentUser = UserUtils.getCurrentUser() ?: return
        binding.apply {
            edtName.setText(currentUser.name)
            edtSurname.setText(currentUser.surname)
            edtAge.setText(currentUser.age)
        }
    }

    private fun setOnClickListeners() {
        binding.imgBack.setOnClickListener {
            navigateBack()
        }
        binding.btnSave.setOnClickListener {
            val name = binding.edtName.text.toString()
            val surname = binding.edtSurname.text.toString()
            val age = binding.edtAge.text.toString()
            viewModel.saveUser(name, surname, age)
        }
    }

    private fun clearFields() {
        binding.apply {
            edtName.text.clear()
            edtSurname.text.clear()
            edtName.text.clear()
        }
        setEditTexts()
    }


}