package com.bend.twitterben.start.search.presentation

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.bend.twitterben.start.register.presentation.User

class SearchViewModel : ViewModel() {
  private val usersLiveData = MutableLiveData<List<User>>()

  fun updateUser(users: List<User>){
    usersLiveData.postValue(users)
  }

  fun observerUser(lifeCycle: Lifecycle, observer: (List<User>) -> Unit) {
    usersLiveData.observe({lifeCycle}) {
      it?.let(observer)
    }
  }
}