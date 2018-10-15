package com.bend.twitterben.start.lastmessage.presentation

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.bend.twitterben.start.messagesroom.view.Message

class LastMessagesViewModel: ViewModel() {

  private val messagesLiveData = MutableLiveData<List<Message>>()

  fun updateMessages(state: List<Message>) {
    messagesLiveData.postValue(state)
  }

  fun observeMessagesLiveData(lifecycle: Lifecycle,observer: (List<Message>)-> Unit) {
    messagesLiveData.observe({lifecycle}) {
      it?.let(observer)
    }
  }
}