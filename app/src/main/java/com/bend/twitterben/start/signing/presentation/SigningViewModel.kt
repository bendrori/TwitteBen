package com.bend.twitterben.start.signing.presentation

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel

class SigningViewModel : ViewModel(), ISigningObservers {
  private val signingStateObservers = MutableLiveData<Boolean>()

  override fun signingStateObserve(lifecycle: Lifecycle,observer: (Boolean?) -> Unit) {
    signingStateObservers.observe({lifecycle}) {
      it.let(observer)
    }
  }

  fun updateSigningState(state: Boolean) {
    signingStateObservers.postValue(state)
  }
}