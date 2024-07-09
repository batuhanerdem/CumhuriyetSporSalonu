package com.example.cumhuriyetsporsalonu.domain.mappers

import com.example.cumhuriyetsporsalonu.domain.model.VerifiedStatus

object VerifiedStatusMapper {
    fun String.toVerifiedStatus(): VerifiedStatus? {
        return when (this) {
            "Verified" -> VerifiedStatus.VERIFIED
            "Not Answered" -> VerifiedStatus.NOTANSWERED
            "Denied" -> VerifiedStatus.DENIED
            else -> null
        }
    }

    fun Boolean.toVerifiedStatus(): VerifiedStatus {
        return when (this) {
            true -> VerifiedStatus.VERIFIED
            false -> VerifiedStatus.DENIED
        }

    }
}
