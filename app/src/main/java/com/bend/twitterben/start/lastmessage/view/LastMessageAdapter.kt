package com.bend.twitterben.start.lastmessage.view

import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.ViewHolder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bend.twitterben.R
import com.bend.twitterben.start.messagesroom.view.Message
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.message_cell.view.lastMessageTextViewMessageCellId
import kotlinx.android.synthetic.main.message_cell.view.nameTextViewMessageCellId
import kotlinx.android.synthetic.main.message_cell.view.profileImageViewMessageCellId

class LastMessageAdapter(var messagesList: List<Message>,
  val onClickListener: UserClickListener): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
  override fun onCreateViewHolder(parent: ViewGroup, position: Int): ViewHolder {
    val layoutInflater = LayoutInflater.from(parent.context)
    val view = layoutInflater.inflate(R.layout.message_cell, parent, false)
    return MessageCellHolder(view)
  }

  override fun getItemCount() = messagesList.size

  override fun onBindViewHolder(messageCellHolder: ViewHolder, position: Int) {
    (messageCellHolder as MessageCellHolder).onBind(message = messagesList[position])
  }

  fun update(messages: List<Message>) {
    this.messagesList = messages
    notifyDataSetChanged()
  }

  interface UserClickListener {
    fun onListItemClick(message: Message)
  }

  override fun getItemViewType(position: Int) = position

  inner class MessageCellHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val imageProfile = itemView.profileImageViewMessageCellId
    private val nameText = itemView.nameTextViewMessageCellId
    private val lastMessageText = itemView.lastMessageTextViewMessageCellId

    fun onBind(message: Message) {
      nameText.text = message.name
      lastMessageText.text = message.message
      Glide.with(itemView).load(message.urlImageForLast).into(imageProfile)
      itemView.setOnClickListener {
        onClickListener.onListItemClick(message)
      }
    }
  }
}
