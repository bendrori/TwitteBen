package com.bend.twitterben.start.search.domain

import com.bend.twitterben.start.firebase.UsersRepository
import com.bend.twitterben.start.register.presentation.User

class SearchModel(private val repository: UsersRepository) {

  fun getUsersBySearch(observer: (List<User>) -> Unit) {
    repository.startListener {
      it?.let { it1 -> observer(it1) }
    }
  }

  fun updateFollow(user: User) {
    repository.updateUser("${repository.currentUser?.uid}/following/${user.uuid}", user)
    repository.updateUser("${user.uuid}/followers/${repository.currentUser?.uid}", user)
    repository.updateFollowingNumber()
    repository.updateFollowersNumber()
  }

  fun unFollowUser(user: User) {
    repository.removeValue("${repository.currentUser?.uid}/following/${user.uuid}")
    repository.removeValue("${user.uuid}/followers/${repository.currentUser?.uid}")
    repository.updateFollowingNumber()
    repository.updateFollowersNumber()
  }
}