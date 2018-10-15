package com.bend.twitterben.start.search.view

import android.annotation.SuppressLint
import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.bend.twitterben.R
import com.bend.twitterben.start.register.presentation.User
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.user_find_cell.view.findCellFollowButtonId
import kotlinx.android.synthetic.main.user_find_cell.view.findCellTextViewId

class SearchAdapter(
  private var users :List<User>,
  private val listItemClickListener : UserFollowClickListener
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
  override fun onCreateViewHolder(parent: ViewGroup, position: Int): RecyclerView.ViewHolder {
    val layoutInflater = LayoutInflater.from(parent.context)
    val view = layoutInflater.inflate(R.layout.user_find_cell, parent, false)
    return SearchCellHolder(view)
  }

  override fun getItemCount() = users.size

  override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
    val isFollowing =
      users[position].followers?.containsKey(FirebaseAuth.getInstance().currentUser?.uid)
    if(isFollowing != null)
      (viewHolder as SearchCellHolder).onBind(user = users[position], isFollowing = isFollowing)
    else
      (viewHolder as SearchCellHolder).onBind(user = users[position], isFollowing = false)

  }

  fun update(usersList: List<User>){
    this.users = usersList
    notifyDataSetChanged()
  }

  override fun getItemViewType(position: Int): Int {
    return position
  }

  interface UserFollowClickListener {
    fun onListItemClick(user: User, addUser: Boolean)
  }

  inner class SearchCellHolder(itemView: View): RecyclerView.ViewHolder(itemView){
    private val nameTextView = itemView.findCellTextViewId
    private val followButton = itemView.findCellFollowButtonId

    fun onBind(user : User,isFollowing: Boolean){
      nameTextView.text = user.name

      if(isFollowing)
        setButtonToFollowing(followButton, user = user)
      else
        setButtonToFollow(followButton, user = user)
      }
    }

    @SuppressLint("SetTextI18n")
    fun setButtonToFollowing(followButton : Button,user: User): Boolean {
      followButton.setBackgroundResource(R.drawable.press_follow_button)
      followButton.setTextColor(Color.parseColor("#FFFFFF"))
      followButton.text = "Following"
      followButton.setOnClickListener{
        listItemClickListener.onListItemClick(user,false)
        notifyDataSetChanged()
      }
      return false
    }

    @SuppressLint("SetTextI18n")
    fun setButtonToFollow(followButton : Button,user: User): Boolean {
      followButton.setBackgroundResource(R.drawable.radius_button_follow)
      followButton.setTextColor(Color.parseColor("#4ba5ee"))
      followButton.text = "Follow"
      followButton.setOnClickListener{
        listItemClickListener.onListItemClick(user,true)
        notifyDataSetChanged()
      }
      return true
    }

  }