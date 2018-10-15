package com.bend.twitterben.start.selection.domain

import com.bend.twitterben.start.firebase.UsersRepository
import com.bend.twitterben.start.register.presentation.User

class SelectionModel(private val repository: UsersRepository) {

  fun loadUsers(observer: (List<User>) -> Unit) {
    repository.startListener { userList ->
      if (userList != null) {
        observer(userList.filter { it.uuid != repository.currentUser?.uid})
      }
    }
  }

  fun updateUser(path: String, value: Any) {
    repository.updateUser(path,value)
  }

  fun getCurrentUser() = repository.currentUser

  fun updateFollowersNumber(){
    repository.updateFollowersNumber()
  }

  fun updateFollowingNumber(){
    repository.updateFollowingNumber()
  }
}
