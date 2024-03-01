package com.example.cumhuriyetsporsalonu.utils.user

import com.example.cumhuriyetsporsalonu.domain.model.User

object UserUtils {
    private var currentUser: User? = null
    fun getCurrentUser(): User? {
        return currentUser
    }

    fun setCurrentUser(user: User) {
        currentUser = user
    }
}