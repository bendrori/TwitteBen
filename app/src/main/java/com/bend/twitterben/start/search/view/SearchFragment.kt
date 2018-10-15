package com.bend.twitterben.start.search.view

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup

import com.bend.twitterben.R
import android.support.v7.widget.SearchView.OnQueryTextListener
import android.widget.EditText
import com.bend.twitterben.start.firebase.UsersRepository
import com.bend.twitterben.start.register.presentation.User
import com.bend.twitterben.start.search.domain.SearchModel
import com.bend.twitterben.start.search.presentation.SearchPresenter
import com.bend.twitterben.start.search.presentation.SearchViewModel
import kotlinx.android.synthetic.main.fragment_search.searchRecyclerViewId

class SearchFragment : Fragment() ,SearchAdapter.UserFollowClickListener{


  val presenter = SearchPresenter(SearchModel(UsersRepository()),SearchViewModel())
  var adapter : SearchAdapter? = null

  companion object {
    @JvmField
    val TAG: String = SearchFragment::class.java.name

    @JvmStatic
    fun newInstance() = SearchFragment()
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setHasOptionsMenu(true)
  }

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?): View? {
    return inflater.inflate(R.layout.fragment_search, container, false)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    searchRecyclerViewId.layoutManager = LinearLayoutManager(context)

    searchRecyclerViewId.addItemDecoration(
        DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
    )

    adapter = SearchAdapter(listOf(),this)

    searchRecyclerViewId.adapter = adapter

    presenter.viewModel.observerUser(lifecycle) {
        adapter!!.update(it)
    }
  }

  override fun onPrepareOptionsMenu(menu: Menu) {
    val menuItemSnapshot = menu.findItem(R.id.search_toolbar)
    val menuItem = menu.findItem(R.id.action_settings)
    menuItem.isEnabled = false
    menuItem.isVisible = false
    menuItemSnapshot?.isVisible = true
    if(menuItemSnapshot != null) {
      val searchView = menuItemSnapshot.actionView as android.support.v7.widget.SearchView
      val editText = searchView.findViewById<EditText>(android.support.v7.appcompat.R.id.search_src_text)
      editText.hint = "Search People"
      searchView.setOnQueryTextListener(object : OnQueryTextListener {
        override fun onQueryTextSubmit(userName: String): Boolean {
          presenter.findUser(userName)
          return false
        }

        override fun onQueryTextChange(p0: String): Boolean {
          if(p0.isEmpty())
            adapter?.update(listOf())
          else
            presenter.findUser(p0)
          return false
        }
      })
    }
  }

  override fun onListItemClick(user: User,addUser: Boolean) {
      presenter.updateFollow(user,addUser)
  }

}