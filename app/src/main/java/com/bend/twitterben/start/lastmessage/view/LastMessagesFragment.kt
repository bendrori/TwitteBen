package com.bend.twitterben.start.lastmessage.view

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.bend.twitterben.R
import com.bend.twitterben.start.messagesselection.view.MessagesSelectionActivity
import kotlinx.android.synthetic.main.fragment_messages.messagesRecyclerViewId
import android.os.Build.VERSION_CODES
import android.support.annotation.RequiresApi
import com.bend.twitterben.start.firebase.MessagesRepository
import com.bend.twitterben.start.lastmessage.domain.LastMessageModel
import com.bend.twitterben.start.lastmessage.presentation.LastMessagesPresenter
import com.bend.twitterben.start.lastmessage.presentation.LastMessagesViewModel
import com.bend.twitterben.start.messagesroom.view.Message
import com.bend.twitterben.start.messagesroom.view.MessagesRoomActivity

class LastMessagesFragment : Fragment() , LastMessageAdapter.UserClickListener {


  private val presenter = LastMessagesPresenter(LastMessageModel(MessagesRepository()),
      LastMessagesViewModel()
  )
  companion object {
    @JvmField
    val TAG: String = LastMessagesFragment::class.java.name

    @JvmStatic
    fun newInstance() = LastMessagesFragment()
  }

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?): View? {
    return inflater.inflate(R.layout.fragment_messages, container, false)
  }

  @RequiresApi(VERSION_CODES.JELLY_BEAN)
  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    (activity?.findViewById(R.id.fab) as FloatingActionButton).setImageDrawable(
        ContextCompat.getDrawable(context!!, R.drawable.ic_mail_outline_white_48dp))


    (activity?.findViewById(R.id.fab) as FloatingActionButton).setOnClickListener {
      val intent = Intent(context,MessagesSelectionActivity::class.java)
      startActivity(intent)
    }

    messagesRecyclerViewId.layoutManager = LinearLayoutManager(context)

    messagesRecyclerViewId.addItemDecoration(
        DividerItemDecoration(context, DividerItemDecoration.VERTICAL))

    val adapter = LastMessageAdapter(listOf(),this)

    presenter.getMessages()

    messagesRecyclerViewId.adapter = adapter

    presenter.viewModel.observeMessagesLiveData(lifecycle) {
      adapter.update(it)
    }

  }

  override fun onListItemClick(message: Message) {
    val intent = Intent(context,MessagesRoomActivity::class.java)
    intent.putExtra("toId", message.toId)
    startActivity(intent)
  }

}