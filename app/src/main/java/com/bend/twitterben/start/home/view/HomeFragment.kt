package com.bend.twitterben.start.home.view

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bend.twitterben.R
import com.bend.twitterben.start.home.presentation.HomePresenter
import com.bend.twitterben.start.home.presentation.HomeViewModel
import com.bend.twitterben.start.tweet.view.TweetActivity
import kotlinx.android.synthetic.main.fragment_feed.recyclerViewFeedCellId
import android.support.design.widget.FloatingActionButton
import android.support.v4.content.ContextCompat
import com.bend.twitterben.start.firebase.TweetsRepository
import com.bend.twitterben.start.firebase.UsersRepository
import com.bend.twitterben.start.home.domain.HomeModel
import com.bend.twitterben.start.home.presentation.hideLike
import com.bend.twitterben.start.home.presentation.showLike

class HomeFragment : Fragment() , HomeCellAdapter.ShareTwitteClickListener {

  var presenter = HomePresenter(HomeModel(UsersRepository(), TweetsRepository()), HomeViewModel())

  companion object {

    @JvmField
    val TAG: String = HomeFragment::class.java.name

    @JvmStatic
    fun newInstance() = HomeFragment()
  }

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?): View? {
    return inflater.inflate(R.layout.fragment_feed, container, false)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    (activity?.findViewById(R.id.fab) as FloatingActionButton).setImageDrawable(
        ContextCompat.getDrawable(context!!, R.drawable.ic_new_twitte_white))
    presenter.getTweetsList()

    recyclerViewFeedCellId.layoutManager = LinearLayoutManager(context)

    //    recyclerViewFeedCellId.addItemDecoration(MarginItemDecoration(20))
    ////        DividerItemDecoration(context, DividerItemDecoration.VERTICAL))

    val adapter = HomeCellAdapter(arrayListOf(), this)
    recyclerViewFeedCellId.adapter = adapter

    presenter.viewModel.observerFeeds(lifecycle){
      adapter.update(it)
    }

    (activity?.findViewById(R.id.fab) as FloatingActionButton).setOnClickListener {
      val intent = Intent(context, TweetActivity::class.java)
      startActivity(intent)
    }

  }

  override fun onListItemClick(twitte: Twitte) {
    val sharingIntent = Intent(android.content.Intent.ACTION_SEND)
    sharingIntent.type = "text/plain"
    val shareBody = "Here is the share content body"
    sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject Here")
    sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody)
    startActivity(Intent.createChooser(sharingIntent, "Share via"))
  }

  override fun addLikeToTwitte(twitte: Twitte) {
    presenter.handelLike(twitte.id)
  }
}