package com.bend.twitterben.start.messagesroom.view

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import com.bend.twitterben.R
import com.bend.twitterben.start.firebase.MessagesRepository
import com.bend.twitterben.start.messagesroom.domain.MessagesRoomModel
import com.bend.twitterben.start.messagesroom.presentation.MessagesRoomPresenter
import com.bend.twitterben.start.messagesroom.presentation.MessagesRoomViewModel
import kotlinx.android.synthetic.main.activity_messages_room.messageToolbar
import kotlinx.android.synthetic.main.activity_messages_room.messagesRoomEditTextId
import kotlinx.android.synthetic.main.activity_messages_room.messagesRoomRecyclerViewId
import kotlinx.android.synthetic.main.activity_messages_room.messagesRoomSendButtonId

class MessagesRoomActivity : AppCompatActivity() {

  private val presenter = MessagesRoomPresenter(MessagesRoomModel(MessagesRepository()),MessagesRoomViewModel())

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_messages_room)
    setSupportActionBar(messageToolbar)

    supportActionBar?.setDisplayHomeAsUpEnabled(true)
    supportActionBar?.setDisplayShowHomeEnabled(true)

    messagesRoomRecyclerViewId.layoutManager = LinearLayoutManager(this)

    val adapter = MessagesRoomAdapter(listOf())

    messagesRoomRecyclerViewId.adapter = adapter

    val toId = intent.getStringExtra("toId")

    presenter.getMessages(toId)

    presenter.viewModel.observeMessagesLiveData(lifecycle){
      adapter.update(it)
      messagesRoomRecyclerViewId.scrollToPosition(adapter.itemCount - 1)
    }

    messagesRoomSendButtonId.setOnClickListener {
      presenter.writeMessage(messagesRoomEditTextId.text.toString(),toId)
      messagesRoomEditTextId.text.clear()
    }
  }

  override fun onSupportNavigateUp(): Boolean {
    onBackPressed()
    return true
  }
}
