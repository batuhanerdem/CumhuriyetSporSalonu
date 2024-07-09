package com.example.cumhuriyetsporsalonu.domain.model

import android.os.Parcel
import android.os.Parcelable

enum class VerifiedStatus(val asString: String) : Parcelable {
    VERIFIED("Verified"), NOTANSWERED("Not Answered"), DENIED("Denied");

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(asString)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<VerifiedStatus> {
        override fun createFromParcel(parcel: Parcel): VerifiedStatus {
            return when (val value = parcel.readString()) {
                "Verified" -> VERIFIED
                "Not Answered" -> NOTANSWERED
                "Denied" -> DENIED
                else -> throw IllegalArgumentException("Unknown VerifiedStatus: $value")
            }
        }

        override fun newArray(size: Int): Array<VerifiedStatus?> {
            return arrayOfNulls(size)
        }
    }
}