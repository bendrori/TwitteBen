package com.bend.twitterben.start.messagesselection.view

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bend.twitterben.R
import com.bend.twitterben.start.register.presentation.User
import com.bend.twitterben.start.search.view.SearchAdapter.UserFollowClickListener
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.message_cell_selected.view.messageSelectedNameTextViewCellId
import kotlinx.android.synthetic.main.message_cell_selected.view.profileImageViewCellId
import kotlinx.android.synthetic.main.twit_cell.view.nameTextViewCellId
import kotlinx.android.synthetic.main.twit_cell.view.radioButtonCellId


class MessagesSelectionAdapter(
  private var userList: ArrayList<User>,
  private val listItemClickListener : UserClickListener
): RecyclerView.Adapter<MessagesSelectionAdapter.ViewHolder>() {

  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    holder.onBind(userList[position])
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    val v = LayoutInflater.from(parent.context).inflate(R.layout.message_cell_selected, parent, false)
    return ViewHolder(v)
  }

  override fun getItemCount(): Int {
    return userList.size
  }

  fun update(usersList: ArrayList<User>){
    this.userList = usersList
    notifyDataSetChanged()
  }

  interface UserClickListener {
    fun onListItemClick(user: User)
  }

  inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

    private val nameTextView = itemView.messageSelectedNameTextViewCellId
    private val image = itemView.profileImageViewCellId

    fun onBind(user: User) {
      nameTextView.text = user.name
      Glide.with(itemView).load(user.urlImage).into(image)
      itemView.setOnClickListener {
        listItemClickListener.onListItemClick(user)
      }
    }
  }
}