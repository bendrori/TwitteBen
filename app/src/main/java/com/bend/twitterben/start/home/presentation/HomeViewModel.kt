package com.bend.twitterben.start.home.presentation

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.bend.twitterben.start.home.view.Twitte

class HomeViewModel : ViewModel() {

  val feedsLiveData = MutableLiveData<ArrayList<Twitte>>()
  val likeLiveData = MutableLiveData<LikeState>()
  var isLiked = true

  fun updateFeeds(state: ArrayList<Twitte>) {
    feedsLiveData.postValue(state)
  }

  fun updateLike(state: LikeState) {
    likeLiveData.postValue(state)
  }

  fun observerFeeds(lifeCycle: Lifecycle, observer: (ArrayList<Twitte>) -> Unit) {
    feedsLiveData.observe({lifeCycle}) {
      it?.let(observer)
    }
  }

  fun observerLikeState(lifeCycle: Lifecycle, observer: (LikeState) -> Unit) {
    likeLiveData.observe({lifeCycle}) {
      it?.let(observer)
    }
  }
}