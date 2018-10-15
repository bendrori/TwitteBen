package com.bend.twitterben.start.messagesroom.domain

import com.bend.twitterben.start.firebase.MessagesRepository
import com.bend.twitterben.start.messagesroom.view.Message

class MessagesRoomModel(private val messagesRepository: MessagesRepository) {

  fun getMessages(toId:  String,observer: (List<Message>) -> Unit) {
    messagesRepository.startListener( toId) {
      if (it != null) {
        observer(it)
      }
    }
  }

  fun writeMessage(message: String,toId:  String) {
    messagesRepository.writeMessage(message, toId)
  }
}