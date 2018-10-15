package com.bend.twitterben.start.messagesroom.view

import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.ViewHolder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bend.twitterben.R
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.from_message_cell.view.fromMessageImageId
import kotlinx.android.synthetic.main.from_message_cell.view.fromMessageTextId
import kotlinx.android.synthetic.main.to_message_cell.view.toMessageImageId
import kotlinx.android.synthetic.main.to_message_cell.view.toMessageTextId

class MessagesRoomAdapter(private var messagesList: List<Message>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
  override fun onCreateViewHolder(parent: ViewGroup, position: Int): ViewHolder {
    val layoutInflater = LayoutInflater.from(parent.context)
    return if(messagesList[position].isTo)
      ToCellHolder(layoutInflater.inflate(R.layout.to_message_cell, parent, false))
    else
      FromCellHolder(layoutInflater.inflate(R.layout.from_message_cell, parent, false))

  }

  override fun getItemCount() = messagesList.size

  override fun onBindViewHolder(messageCellHolder: ViewHolder, position: Int) {
    if(messagesList[position].isTo)
      (messageCellHolder as ToCellHolder).onBind(message = messagesList[position])
    else
      (messageCellHolder as FromCellHolder).onBind(message = messagesList[position])

  }

  override fun getItemViewType(position: Int) = position

  fun update(messageList: List<Message>){
    this.messagesList = messageList
    notifyDataSetChanged()
  }

  inner class ToCellHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val imageProfileTo = view.toMessageImageId
    private val messageText = view.toMessageTextId

    fun onBind(message: Message) {
      messageText.text = message.message
      Glide.with(itemView).load(message.urlImage).into(imageProfileTo)
    }
  }

  inner class FromCellHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val imageProfileFrom = view.fromMessageImageId
    private val messageText = view.fromMessageTextId

    fun onBind(message: Message) {
      messageText.text = message.message
      Glide.with(itemView).load(message.urlImage).into(imageProfileFrom)
    }
  }
}

class Message(
  val name: String = "",
  val message: String = "",
  val fromId: String = "",
  val toId: String = "",
  var isTo: Boolean = false,
  var urlImage: String = "",
  var urlImageForLast: String = ""

)