package com.bend.twitterben.start.selection.presentation

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.bend.twitterben.start.register.presentation.User

class SelectionViewModel : ViewModel() {

  private val userLiseLiveData = MutableLiveData<ArrayList<User>>()

  fun updateUsers(state: ArrayList<User>) {
    userLiseLiveData.postValue(state)
  }

  fun observerUsers(lifeCycle: Lifecycle, observer: (ArrayList<User>) -> Unit) {
    userLiseLiveData.observe({lifeCycle}) {
      it?.let(observer)
    }
  }
}