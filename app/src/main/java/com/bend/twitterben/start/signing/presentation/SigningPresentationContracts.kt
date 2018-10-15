package com.bend.twitterben.start.signing.presentation

import android.arch.lifecycle.Lifecycle

interface ISigningPresenter : ISigningObservers{
  fun singingUser(email: String, password: String)
}

interface ISigningObservers {
  fun signingStateObserve(lifecycle: Lifecycle,observer: (Boolean?) -> Unit)
}