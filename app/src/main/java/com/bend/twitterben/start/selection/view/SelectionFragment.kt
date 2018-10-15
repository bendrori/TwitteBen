package com.bend.twitterben.start.selection.view

import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.bend.twitterben.R
import com.bend.twitterben.start.firebase.UsersRepository
import com.bend.twitterben.start.selection.domain.SelectionModel
import com.bend.twitterben.start.selection.presentation.SelectionPresenter
import com.bend.twitterben.start.selection.presentation.SelectionViewModel
import kotlinx.android.synthetic.main.fragment_choose_followers.mainRecyclerViewId

class SelectionFragment : Fragment() {

  private var presenter : SelectionPresenter = SelectionPresenter(
      model = SelectionModel(UsersRepository()),
      viewModel = SelectionViewModel())

  companion object {

    @JvmField
    val TAG: String = SelectionFragment::class.java.name

    @JvmStatic
    fun newInstance() = SelectionFragment()
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    (activity?.findViewById(R.id.fab) as FloatingActionButton).setImageDrawable(
        ContextCompat.getDrawable(context!!, R.drawable.ic_foot_choose_followers))

    mainRecyclerViewId.layoutManager = LinearLayoutManager(context)

    mainRecyclerViewId.addItemDecoration(
        DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
    )

    presenter.getUserList()

    val adapter = SelectionCellAdapter(arrayListOf())

    mainRecyclerViewId.adapter = adapter

    presenter.viewModel.observerUsers(lifecycle) {
      adapter.update(it)
    }

    (activity?.findViewById(R.id.fab) as FloatingActionButton).setOnClickListener {
      presenter.updateSelectedUsers(adapter.selectedUsers)
    }
  }

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    return inflater.inflate(R.layout.fragment_choose_followers, container, false)
  }
}
