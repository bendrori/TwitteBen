package com.bend.twitterben.start.register.presentation

import android.arch.lifecycle.Lifecycle

data class User(
  val uuid: String = "",
  val name: String = "",
  val email: String = "",
  val followers: HashMap<String, Any>? = null,
  val followersNumber: Long = 0,
  val following: HashMap<String, Any>? = null,
  val followingNumber: Long = 0,
  val skipIntro: Boolean = false,
  val urlImage: String = ""
)

interface IRegisterPresenter :IRegisterObservers {
  fun registerUser(name: String, email: String, password: String, url: String)
}

interface IRegisterObservers {
  fun registerStateObserve(lifecycle: Lifecycle,observer: (Boolean?) -> Unit)
}