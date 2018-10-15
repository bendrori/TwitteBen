package com.bend.twitterben.start.home.presentation

sealed class LikeState
object showLike : LikeState()
object hideLike : LikeState()