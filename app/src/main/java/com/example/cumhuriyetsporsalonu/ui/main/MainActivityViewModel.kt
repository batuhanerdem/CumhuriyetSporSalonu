package com.example.cumhuriyetsporsalonu.ui.main

import com.example.cumhuriyetsporsalonu.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor() : BaseViewModel<MainActionBus>() {}