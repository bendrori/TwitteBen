package com.bend.twitterben.start.main.view

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.bend.twitterben.R
import com.bend.twitterben.start.selection.view.SelectionFragment
import com.bend.twitterben.start.main.presentation.MainPresenter
import com.bend.twitterben.start.main.presentation.MainViewModel
import com.bend.twitterben.start.main.presentation.ShowSelection
import com.bend.twitterben.start.main.presentation.ShowHome
import com.bend.twitterben.start.home.view.HomeFragment
import com.bend.twitterben.start.start.view.StartActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import android.widget.TextView
import com.bend.twitterben.start.main.presentation.ShowMessages
import com.bend.twitterben.start.main.presentation.ShowNotifications
import com.bend.twitterben.start.main.presentation.ShowSearch
import com.bend.twitterben.start.lastmessage.view.LastMessagesFragment
import com.bend.twitterben.start.notifications.view.NotificationsFragment
import com.bend.twitterben.start.register.presentation.User
import com.bend.twitterben.start.search.view.SearchFragment
import com.bumptech.glide.Glide
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.content_main.mainBottomNavigationViewId

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

  private var presenter :MainPresenter = MainPresenter(viewModel = MainViewModel())

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    setSupportActionBar(toolbar)
    supportActionBar?.setDisplayShowTitleEnabled(false)

    presenter.getUser()
    presenter.getSkipIntro()

    presenter.viewModel.observerMainScreen(lifecycle) {
      when(it) {
        ShowSelection -> {
          showSelectionFragment()
          hideDrawerLayout()
          hideMenuSettingsItem()
          hideMenuSearchItem()
          mainBottomNavigationViewId.visibility = View.GONE
        }
        ShowHome -> {
          showHomeFragment()
          showDrawerLayout()
          showMenuSettingsItem()
          hideMenuSearchItem()
          mainBottomNavigationViewId.visibility = View.VISIBLE
        }
        ShowSearch ->showSearchFragment()

        ShowNotifications -> {
          showNotificationsFragment()
          hideMenuSearchItem()
          showMenuSettingsItem()
        }

        ShowMessages -> {
          showMessagesFragment()
          hideMenuSearchItem()
          showMenuSettingsItem()
        }
      }
    }
    presenter.viewModel.observerUser(lifecycle){
      setDrawerInfo(it)
    }

    mainBottomNavigationViewId.setOnNavigationItemSelectedListener { item ->
      when (item.itemId) {
        R.id.action_home ->  presenter.viewModel.updateMainScreen(ShowHome)
        R.id.action_search -> presenter.viewModel.updateMainScreen(ShowSearch)
        R.id.action_notifications -> presenter.viewModel.updateMainScreen(ShowNotifications)
        R.id.action_messages->  presenter.viewModel.updateMainScreen(ShowMessages)
      }
       true
    }
  }

  private fun setDrawerInfo(user: User) {
    val navigationView = findViewById<View>(R.id.nav_view) as NavigationView
    val headerView = navigationView.getHeaderView(0)
    val navUsername = headerView.findViewById(R.id.navUsernameId) as TextView
    val followersInfo = headerView.findViewById(R.id.navFollowersInfoTextViewId) as TextView
    val image = headerView.findViewById(R.id.navProfileImageViewId) as CircleImageView
    navUsername.text = user.email.substringBefore('@')
    followersInfo.text = "${user.followingNumber} Following ${user.followersNumber} Followers "
    Glide.with(this).load(user.urlImage).into(image)
  }

  override fun onBackPressed() {
    if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
      drawer_layout.closeDrawer(GravityCompat.START)
    } else {
      for (i in supportFragmentManager.backStackEntryCount - 2 downTo 1) {
        supportFragmentManager.popBackStack()
      }
      mainBottomNavigationViewId.menu.getItem(0).isChecked = true
      super.onBackPressed()
    }
  }

  override fun onCreateOptionsMenu(menu: Menu): Boolean {
    menuInflater.inflate(R.menu.main, menu)
    return true
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    return when (item.itemId) {
      R.id.action_settings -> true
      else -> super.onOptionsItemSelected(item)
    }
  }

  override fun onNavigationItemSelected(item: MenuItem): Boolean {
    when (item.itemId) {
      R.id.nav_profile -> {
      }
      R.id.nav_lists -> {

      }
      R.id.nav_bookmarks -> {

      }
      R.id.nav_moments -> {

      }
      R.id.nav_send -> {
        presenter.signOut()
        val intent = Intent(this, StartActivity::class.java)
        startActivity(intent)
        finish()
      }
    }
    drawer_layout.closeDrawer(GravityCompat.START)
    return true
  }

  private fun showSelectionFragment() {
    supportFragmentManager.beginTransaction()
        .replace(R.id.mainContainerId,
            SelectionFragment.newInstance(),
            SelectionFragment.TAG)
        .addToBackStack(SelectionFragment.TAG)
        .commit()
  }

  private fun showHomeFragment() {
    supportFragmentManager.beginTransaction()
        .replace(R.id.mainContainerId,
            HomeFragment.newInstance(),
            HomeFragment.TAG)
        .addToBackStack(HomeFragment.TAG)
        .commit()
  }

  private fun showSearchFragment() {
    supportFragmentManager.beginTransaction()
        .replace(R.id.mainContainerId,
            SearchFragment.newInstance(),
            SearchFragment.TAG)
        .addToBackStack(SearchFragment.TAG)
        .commit()
  }

  private fun showNotificationsFragment() {
    supportFragmentManager.beginTransaction()
        .replace(R.id.mainContainerId,
            NotificationsFragment.newInstance(),
            NotificationsFragment.TAG)
        .addToBackStack(NotificationsFragment.TAG)
        .commit()
  }

  private fun showMessagesFragment() {
    supportFragmentManager.beginTransaction()
        .replace(R.id.mainContainerId,
            LastMessagesFragment.newInstance(),
            LastMessagesFragment.TAG)
        .addToBackStack(LastMessagesFragment.TAG)
        .commit()
  }

  private fun showDrawerLayout() {
    val toggle = ActionBarDrawerToggle(
        this,
        drawer_layout, toolbar,
        R.string.navigation_drawer_open,
        R.string.navigation_drawer_close
    )
    drawer_layout.addDrawerListener(toggle)
    toggle.syncState()
    nav_view.setNavigationItemSelectedListener(this)
  }
  private fun hideDrawerLayout() {
    val toggle = ActionBarDrawerToggle(
        this,
        drawer_layout, null,
        R.string.navigation_drawer_open,
        R.string.navigation_drawer_close
    )
    drawer_layout.addDrawerListener(toggle)
    toggle.syncState()
    nav_view.setNavigationItemSelectedListener(this)
  }

  private fun showMenuSettingsItem(){
    val menuItemSnapshot = toolbar.menu.findItem(R.id.action_settings)
      if(menuItemSnapshot != null) {
        menuItemSnapshot.isEnabled = true
        menuItemSnapshot.isVisible = true
    }
  }

  private fun hideMenuSettingsItem(){
    val menuItemSnapshot = toolbar.menu.findItem(R.id.action_settings)
    if(menuItemSnapshot != null) {
      menuItemSnapshot.isEnabled = false
      menuItemSnapshot.isVisible = false
    }
  }

  private fun hideMenuSearchItem() {
    val menuItemSnapshot = toolbar.menu.findItem(R.id.search_toolbar)
    if(menuItemSnapshot != null) {
      menuItemSnapshot.isVisible = false
    }
  }
}
