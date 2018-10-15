package com.bend.twitterben.start.main.presentation

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.bend.twitterben.start.register.presentation.User

class MainViewModel : ViewModel() {

  private val mainScreenLiveData = MutableLiveData<MainScreen>()
  var showMenu: Boolean = false
  private val userLiveData = MutableLiveData<User>()

  fun updateMainScreen(state: MainScreen) {
    mainScreenLiveData.postValue(state)
  }

  fun updateUser(user: User){
    userLiveData.postValue(user)
  }


  fun observerMainScreen(lifeCycle: Lifecycle, observer: (MainScreen) -> Unit) {
    mainScreenLiveData.observe({lifeCycle}) {
      it?.let(observer)
    }
  }

  fun observerUser(lifeCycle: Lifecycle, observer: (User) -> Unit) {
    userLiveData.observe({lifeCycle}) {
      it?.let(observer)
    }
  }
}