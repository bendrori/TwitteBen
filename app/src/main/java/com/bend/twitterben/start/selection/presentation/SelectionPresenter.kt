package com.bend.twitterben.start.selection.presentation

import com.bend.twitterben.start.register.presentation.User
import com.bend.twitterben.start.selection.domain.SelectionModel
import java.util.ArrayList

class SelectionPresenter(val model: SelectionModel,val viewModel: SelectionViewModel) {

  fun getUserList(){
    model.loadUsers {list ->
      viewModel.updateUsers(list as ArrayList<User>)
    }
  }

  fun updateSelectedUsers(users : ArrayList<User>){
    for (user in users) {
      model.updateUser(
          "${model.getCurrentUser()?.uid}/following/${user.uuid}",
          User(
              uuid = user.uuid,
              name = user.email.substringBefore('@'),
              email = user.email))

      model.updateUser(
          "${user.uuid}/followers/${model.getCurrentUser()?.uid}",
          User(
              uuid = model.getCurrentUser()?.uid!!,
              name = model.getCurrentUser()?.email!!.substringBefore('@'),
              email = model.getCurrentUser()?.email!!))
      model.updateFollowersNumber()
    }
    model.updateFollowingNumber()
    model.updateUser("${model.getCurrentUser()?.uid}/skipIntro",true)
  }
}