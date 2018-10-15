package com.bend.twitterben.start.home.domain

import com.bend.twitterben.start.firebase.TweetsRepository
import com.bend.twitterben.start.firebase.UsersRepository
import com.bend.twitterben.start.home.view.Twitte

class HomeModel(
  private val usersRepository: UsersRepository,
  private val tweetsRepository: TweetsRepository) {

  fun getTweets(observer: (List<Twitte>) -> Unit) {
      usersRepository.startFolloingListener { userList ->
            tweetsRepository.startListener { tweetsList ->
              val tweetsArray: ArrayList<Twitte> = arrayListOf()
              if (userList != null) {
                for(user in userList) {
                  tweetsArray.addAll(tweetsList?.filter { it.userUuid == user.uuid }!!)
                }
                tweetsArray.sortByDescending { it.time.toLong() }
                observer(tweetsArray)
              }
            }
      }
    }

  fun userLikeTheTwitte(id: String) {
    tweetsRepository.userLikeTheTwitte(id)
  }

  fun userDontLikeTheTwitte(id: String) {
    tweetsRepository.userDontLikeTheTwitte(id)
  }
}
