package com.bend.twitterben.start.tweet.presentation

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel

class TweetViewModel: ViewModel() {

  private val isDoneLiveData = MutableLiveData<Boolean>()

  fun upDateIsDone(state: Boolean) {
    isDoneLiveData.postValue(state)
  }

  fun observeIsDone(lifecycle: Lifecycle, observer: (Boolean) -> Unit) {
    isDoneLiveData.observe({lifecycle}){
      it?.let(observer)
    }
  }
}