package com.bend.twitterben.start.search.presentation

import com.bend.twitterben.start.register.presentation.User
import com.bend.twitterben.start.search.domain.SearchModel

class SearchPresenter(private val model: SearchModel,val viewModel: SearchViewModel) {

  fun findUser(userName: String) {
    model.getUsersBySearch { list ->
      viewModel.updateUser(list.filter { it.name.contains(userName)})

    }
  }

  fun updateFollow(user: User, addUser: Boolean) {
    if(addUser)
      model.updateFollow(user)
    else
      model.unFollowUser(user)
  }
}