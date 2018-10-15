package com.bend.twitterben.start.lastmessage.presentation

import com.bend.twitterben.start.lastmessage.domain.LastMessageModel

class LastMessagesPresenter(
  private val model: LastMessageModel,
  val viewModel: LastMessagesViewModel) {

  fun getMessages(){
    model.getMessages {
      viewModel.updateMessages(it)
    }
  }
}