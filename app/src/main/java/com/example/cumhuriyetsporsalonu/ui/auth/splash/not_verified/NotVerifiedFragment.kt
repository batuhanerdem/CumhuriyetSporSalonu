package com.example.cumhuriyetsporsalonu.ui.auth.splash.not_verified

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.cumhuriyetsporsalonu.R
import com.example.cumhuriyetsporsalonu.databinding.FragmentEditProfileBinding
import com.example.cumhuriyetsporsalonu.databinding.FragmentNotVerifiedBinding
import com.example.cumhuriyetsporsalonu.domain.model.VerifiedStatus
import com.example.cumhuriyetsporsalonu.domain.model.VerifiedStatus.DENIED
import com.example.cumhuriyetsporsalonu.domain.model.VerifiedStatus.NOTANSWERED
import com.example.cumhuriyetsporsalonu.domain.model.VerifiedStatus.VERIFIED
import com.example.cumhuriyetsporsalonu.ui.base.BaseFragment
import com.example.cumhuriyetsporsalonu.ui.main.MainActivity
import com.example.cumhuriyetsporsalonu.utils.Stringfy
import com.example.cumhuriyetsporsalonu.utils.Stringfy.Companion.stringfy
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class NotVerifiedFragment :
    BaseFragment<NotVerifiedActionBus, NotVerifiedViewModel, FragmentNotVerifiedBinding>(
        FragmentNotVerifiedBinding::inflate, NotVerifiedViewModel::class.java
    ) {
    private val args: NotVerifiedFragmentArgs by navArgs()


    override fun initPage() {
        viewModel.getUserVerifiedStatus(args.userId)
    }

    override suspend fun onAction(action: NotVerifiedActionBus) {
        when (action) {
            NotVerifiedActionBus.Init -> {}
            is NotVerifiedActionBus.ShowError -> {
                showErrorMessage(action.error)
            }

            NotVerifiedActionBus.StatusLoaded -> {
                decideString(viewModel.status)
            }
        }

    }

    private fun decideString(status: VerifiedStatus) {
        when (status) {
            VERIFIED -> {
                CoroutineScope(Dispatchers.Main).launch {
                    setString(
                        R.string.verify_verified_title.stringfy(),
                        R.string.verify_verified_info.stringfy(),
                        R.string.verify_verified.stringfy()
                    )
                    delay(5000)
                    navigateHome()
                }
            }

            NOTANSWERED -> {
                setString(
                    R.string.wait_for_verify_title.stringfy(),
                    R.string.wait_for_verify_info.stringfy(),
                    R.string.wait_for_verify.stringfy()
                )
                setPhoto(R.drawable.photo_waiting)
            }

            DENIED -> {
                setString(
                    R.string.verify_denied_title.stringfy(),
                    R.string.verify_denied_info.stringfy(),
                    R.string.verify_denied.stringfy()
                )
                setPhoto(R.drawable.photo_denied)

            }
        }
    }

    private fun setString(titleString: Stringfy, infoString: Stringfy, paragraphString: Stringfy) {
        binding.tvTitle.text = titleString.getString(requireContext())
        binding.tvInfo.text = infoString.getString(requireContext())
        binding.tvParagraph.text = paragraphString.getString(requireContext())
    }

    private fun setPhoto(resId: Int) {
        binding.imgVerify.setImageResource(resId)
    }

    private fun navigateHome() {
        Intent(requireContext(), MainActivity::class.java).apply {
            startActivity(this)
            requireActivity().finish()
        }
    }

}