package com.bend.twitterben.start.tweet.domain

import com.bend.twitterben.start.firebase.TweetsRepository

class TweetModel(private val tweetsRepository: TweetsRepository) {

  fun writeTweet(tweet: String) {
    tweetsRepository.writeTweet(tweet)
  }
}