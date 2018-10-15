package com.bend.twitterben.start.selection.view

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bend.twitterben.R
import com.bend.twitterben.start.register.presentation.User
import kotlinx.android.synthetic.main.twit_cell.view.nameTextViewCellId
import kotlinx.android.synthetic.main.twit_cell.view.radioButtonCellId

class SelectionCellAdapter(
  private var userList: ArrayList<User>): RecyclerView.Adapter<SelectionCellAdapter.ViewHolder>() {

  var selectedUsers = ArrayList<User>()

  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    holder.onBind(userList[position])
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    val v = LayoutInflater.from(parent.context).inflate(R.layout.twit_cell, parent, false)
    return ViewHolder(v)
  }

  override fun getItemCount(): Int {
    return userList.size
  }

  fun update(usersList: ArrayList<User>){
    this.userList = usersList
    notifyDataSetChanged()
  }

  inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

    private val nameTextView = itemView.nameTextViewCellId
    private val checkBoxViewButton = itemView.radioButtonCellId

    fun onBind(user: User) {
      nameTextView.text = user.name
      itemView.setOnClickListener {
        checkBoxViewButton.isChecked = !checkBoxViewButton.isChecked
        if(checkBoxViewButton.isChecked)
          selectedUsers.add(user)
        else
          selectedUsers.remove(user)
      }
    }
  }
}