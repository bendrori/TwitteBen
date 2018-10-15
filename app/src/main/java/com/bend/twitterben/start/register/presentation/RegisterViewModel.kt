package com.bend.twitterben.start.register.presentation

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel

class RegisterViewModel: ViewModel() ,IRegisterObservers {

  private val registerStateObservers = MutableLiveData<Boolean>()

  override fun registerStateObserve(lifecycle: Lifecycle,observer: (Boolean?) -> Unit) {
    registerStateObservers.observe({lifecycle}) {
      it.let(observer)
    }
  }

  fun updateRegisterState(state: Boolean) {
    registerStateObservers.postValue(state)
  }
}