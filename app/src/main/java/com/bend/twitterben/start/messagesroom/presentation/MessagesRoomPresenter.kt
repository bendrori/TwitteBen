package com.bend.twitterben.start.messagesroom.presentation

import com.bend.twitterben.start.messagesroom.domain.MessagesRoomModel

class MessagesRoomPresenter(private val model: MessagesRoomModel, val viewModel: MessagesRoomViewModel ) {

  fun getMessages(toId: String){
    model.getMessages(toId) {
      viewModel.updateMessages(it)
    }
  }

  fun writeMessage(message: String, toId: String) {
    model.writeMessage(message,toId)
  }

}