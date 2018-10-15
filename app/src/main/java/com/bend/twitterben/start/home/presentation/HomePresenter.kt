package com.bend.twitterben.start.home.presentation

import com.bend.twitterben.start.home.domain.HomeModel
import com.bend.twitterben.start.home.view.Twitte
import kotlin.collections.ArrayList

class HomePresenter(private val model: HomeModel,val viewModel: HomeViewModel) {

  fun getTweetsList(){
    model.getTweets {
      viewModel.updateFeeds(it as ArrayList<Twitte>)
    }
  }

  fun handelLike(id: String) {
    viewModel.isLiked = !viewModel.isLiked
    if(viewModel.isLiked) {
      model.userDontLikeTheTwitte(id)
    }
    else{
      model.userLikeTheTwitte(id)
    }
  }
}