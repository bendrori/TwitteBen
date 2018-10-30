package com.bend.twitterben.start.home.view

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bend.twitterben.R
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.feed_cell.view.dateTextViewFeedCellId
import kotlinx.android.synthetic.main.feed_cell.view.favoriteImageViewFeedCellId
import kotlinx.android.synthetic.main.feed_cell.view.freeTextViewFeedCellId
import kotlinx.android.synthetic.main.feed_cell.view.nameTextViewFeedCellId
import kotlinx.android.synthetic.main.feed_cell.view.numberOfLikesTextViewFeedCellId
import kotlinx.android.synthetic.main.feed_cell.view.profileImageViewFeedCellId
import kotlinx.android.synthetic.main.feed_cell.view.shareButtonFeedCellId
import org.joda.time.Days
import org.joda.time.Hours
import org.joda.time.LocalDateTime
import org.joda.time.Minutes
import org.joda.time.Seconds

class HomeCellAdapter(
  private var feedList: ArrayList<Twitte>,
  private val shareTwitteClickListener : ShareTwitteClickListener
): RecyclerView.Adapter<HomeCellAdapter.ViewHolder>() {

  override fun onCreateViewHolder(parent: ViewGroup, position: Int): ViewHolder {
    val v = LayoutInflater.from(parent.context).inflate(R.layout.feed_cell, parent, false)
    return ViewHolder(v)
  }

  override fun getItemCount() = feedList.size

  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    holder.onBind(feedList[position])
  }

  fun update(feedList: ArrayList<Twitte>){
    this.feedList = feedList
    notifyDataSetChanged()
  }

  interface ShareTwitteClickListener {
    fun onListItemClick(twitte: Twitte)
    fun addLikeToTwitte(twitte: Twitte)
  }

  inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

    private val nameTextView = itemView.nameTextViewFeedCellId
    private val freeTextView = itemView.freeTextViewFeedCellId
    private val dateTextView = itemView.dateTextViewFeedCellId
    private val buttonShare = itemView.shareButtonFeedCellId
    private val favoriteImage = itemView.favoriteImageViewFeedCellId
    private val numberOfLikes = itemView.numberOfLikesTextViewFeedCellId
    private val image = itemView.profileImageViewFeedCellId


    fun onBind(twitte: Twitte) {
      nameTextView.text = twitte.title.substringBefore('@')
      freeTextView.text = twitte.text
      dateTextView.text = getDate(twitte.time.toLong())
      numberOfLikes.text = twitte.likes

      if(twitte.likes.toInt() > 0) {
        if (twitte.likesUsers != null) {
          if (twitte.likesUsers.contains(FirebaseAuth.getInstance().uid))
            Glide.with(itemView).load(R.drawable.ic_favorite_black_24dp).into(favoriteImage)
          else
            Glide.with(itemView).load(R.drawable.ic_favorite_border_black_24dp).into(favoriteImage)
        }
      }else {
        Glide.with(itemView).load(R.drawable.ic_favorite_border_black_24dp).into(favoriteImage)
      }
      buttonShare.setOnClickListener {
        shareTwitteClickListener.onListItemClick(twitte = twitte)
      }
      favoriteImage.setOnClickListener {
        shareTwitteClickListener.addLikeToTwitte(twitte = twitte)
      }
      Glide.with(itemView).load(twitte.urlPic).into(image)
    }
  }
}

private fun getDate(time: Long): String {
  val date1 = LocalDateTime(time)
  val date2 = LocalDateTime.now()

  if(Days.daysBetween(date1, date2).days > 0)
    return "${Days.daysBetween(date1, date2).days}d"
  if(Hours.hoursBetween(date1, date2).hours > 0)
    return "${Hours.hoursBetween(date1, date2).hours}h"
  return if(Minutes.minutesBetween(date1, date2).minutes > 0)
    "${Minutes.minutesBetween(date1, date2).minutes}m"
  else
    "${Seconds.secondsBetween(date1, date2).seconds}s"
}

data class Twitte(
  val id: String = "",
  val likesUsers: HashMap<String, Any>? = null,
  val title: String = "",
  val urlPic: String = "",
  val text: String = "",
  val time: String = "",
  val userUuid: String = "",
  val likes: String = "0"
)