package com.bend.twitterben.start.notifications.view

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.bend.twitterben.R

class NotificationsFragment : Fragment() {

  companion object {
    @JvmField
    val TAG: String = NotificationsFragment::class.java.name

    @JvmStatic
    fun newInstance() = NotificationsFragment()
  }

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?): View? {
    return inflater.inflate(R.layout.fragment_notifications, container, false)
  }
}