package com.bend.twitterben.start.messagesselection.view

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import com.bend.twitterben.R
import com.bend.twitterben.start.firebase.UsersRepository
import com.bend.twitterben.start.messagesroom.view.MessagesRoomActivity
import com.bend.twitterben.start.register.presentation.User
import com.bend.twitterben.start.selection.domain.SelectionModel
import com.bend.twitterben.start.selection.presentation.SelectionPresenter
import com.bend.twitterben.start.selection.presentation.SelectionViewModel
import kotlinx.android.synthetic.main.activity_messages_room.messageToolbar
import kotlinx.android.synthetic.main.activity_messages_selection.messageSelectedRecyclerViewId
import kotlinx.android.synthetic.main.activity_messages_selection.messageSelectedToolbar

class MessagesSelectionActivity : AppCompatActivity() , MessagesSelectionAdapter.UserClickListener{


  private var presenter : SelectionPresenter = SelectionPresenter(
      model = SelectionModel(UsersRepository()),
      viewModel = SelectionViewModel()
  )

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_messages_selection)

    setSupportActionBar(messageSelectedToolbar)

    supportActionBar?.setDisplayHomeAsUpEnabled(true)
    supportActionBar?.setDisplayShowHomeEnabled(true)
    supportActionBar?.setDisplayShowTitleEnabled(false)

    presenter.getUserList()

    messageSelectedRecyclerViewId.layoutManager = LinearLayoutManager(this)

    messageSelectedRecyclerViewId.addItemDecoration(
        DividerItemDecoration(this, DividerItemDecoration.VERTICAL))

    val adapter = MessagesSelectionAdapter(arrayListOf(),this)

    messageSelectedRecyclerViewId.adapter = adapter

    presenter.viewModel.observerUsers(lifecycle) {
      adapter.update(it)
    }
  }

  override fun onListItemClick(user: User) {
   val intent = Intent(this,MessagesRoomActivity::class.java)
    intent.putExtra("toId",user.uuid)
    startActivity(intent)
  }

  override fun onSupportNavigateUp(): Boolean {
    onBackPressed()
    return true
  }
}
