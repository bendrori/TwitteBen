package com.bend.twitterben.start.tweet.presentation

import com.bend.twitterben.start.tweet.domain.TweetModel

class TweetPresenter(private val model: TweetModel, val viewModel: TweetViewModel) {

  fun publishTweet(tweet: String) {
    model.writeTweet(tweet)
    viewModel.upDateIsDone(true)

  }
}
