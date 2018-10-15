package com.bend.twitterben.start.main.presentation

sealed class MainScreen
object ShowHome: MainScreen()
object ShowSearch: MainScreen()
object ShowMessages: MainScreen()
object ShowNotifications: MainScreen()
object ShowSelection : MainScreen()