package com.bend.twitterben.start.tweet.view

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.bend.twitterben.R
import com.bend.twitterben.start.firebase.TweetsRepository
import com.bend.twitterben.start.tweet.domain.TweetModel
import com.bend.twitterben.start.tweet.presentation.TweetPresenter
import com.bend.twitterben.start.tweet.presentation.TweetViewModel
import kotlinx.android.synthetic.main.activity_new_tweet.newTweetButtonId
import kotlinx.android.synthetic.main.activity_new_tweet.newTweetEditTextId

class TweetActivity : AppCompatActivity() {

  private val presenter = TweetPresenter(TweetModel(TweetsRepository()),TweetViewModel())

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_new_tweet)

    supportActionBar?.setDisplayHomeAsUpEnabled(true)

    newTweetButtonId.setOnClickListener {
      presenter.publishTweet(newTweetEditTextId.text.toString().trim())
    }

    presenter.viewModel.observeIsDone(lifecycle){
      if(it)
        finish()
    }
  }
}

