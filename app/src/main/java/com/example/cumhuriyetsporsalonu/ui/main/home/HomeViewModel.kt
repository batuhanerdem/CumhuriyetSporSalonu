package com.example.cumhuriyetsporsalonu.ui.main.home

import com.example.cumhuriyetsporsalonu.domain.model.User
import com.example.cumhuriyetsporsalonu.utils.user.UserUtils
import com.example.cumhuriyetsporsalonu.ui.base.BaseViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

class HomeViewModel : BaseViewModel<HomeActionBus>() {
    val auth = Firebase.auth
    lateinit var currentUser: User
    fun startLoading() {
        sendAction(HomeActionBus.Loading)
    }

    fun getUserInfo() {
        currentUser = UserUtils.getCurrentUser()!!
    }
}