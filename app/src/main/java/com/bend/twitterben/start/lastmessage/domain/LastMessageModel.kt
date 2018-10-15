package com.bend.twitterben.start.lastmessage.domain

import com.bend.twitterben.start.firebase.MessagesRepository
import com.bend.twitterben.start.firebase.UsersRepository
import com.bend.twitterben.start.messagesroom.view.Message
import java.util.Observer

class LastMessageModel(private val messageRepository: MessagesRepository) {

  fun getMessages(observer: (List<Message>) -> Unit) {
    messageRepository.startListenerToLastMessages {
      if (it != null) {
        observer(it)
      }
    }
  }
}