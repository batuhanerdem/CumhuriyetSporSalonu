package com.example.cumhuriyetsporsalonu.ui.auth.splash.not_verified

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.cumhuriyetsporsalonu.R
import com.example.cumhuriyetsporsalonu.databinding.FragmentNotVerifiedBinding
import com.example.cumhuriyetsporsalonu.domain.model.VerifiedStatus.DENIED
import com.example.cumhuriyetsporsalonu.domain.model.VerifiedStatus.NOTANSWERED
import com.example.cumhuriyetsporsalonu.domain.model.VerifiedStatus.VERIFIED
import com.example.cumhuriyetsporsalonu.utils.Stringfy
import com.example.cumhuriyetsporsalonu.utils.Stringfy.Companion.stringfy
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NotVerifiedFragment : Fragment() {
    private lateinit var binding: FragmentNotVerifiedBinding
    private val args: NotVerifiedFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentNotVerifiedBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        decideString()
    }

    private fun decideString() {
        when (args.verifiedStatus) {
            VERIFIED -> {

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
}