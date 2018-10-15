package com.bend.twitterben.start.main.presentation

import com.bend.twitterben.start.register.presentation.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MainPresenter(val viewModel: MainViewModel) {

  fun getUser(){
    val uid = FirebaseAuth.getInstance().currentUser?.uid
    if(uid != null)
    FirebaseDatabase.getInstance()
        .reference.child("Users")
        .child(uid)
        .addValueEventListener(object : ValueEventListener{
      override fun onCancelled(databaseError: DatabaseError) {}

      override fun onDataChange(dataSnapshot: DataSnapshot) {
        val user = dataSnapshot.getValue(User::class.java)
        if(user != null){
          viewModel.updateUser(user)
        }
      }
    })
  }

  fun getSkipIntro() {
    val uid = FirebaseAuth.getInstance().currentUser?.uid
    if(uid != null)
      FirebaseDatabase.getInstance()
          .reference.child("Users")
          .child("$uid/skipIntro")
          .addValueEventListener(object : ValueEventListener{
            override fun onCancelled(databaseError: DatabaseError) {}

            override fun onDataChange(dataSnapshot: DataSnapshot) {
              val skipIntro = dataSnapshot.getValue(Boolean::class.java)
              if(skipIntro != null){
                if(skipIntro){
                  viewModel.updateMainScreen(ShowHome)
                  viewModel.showMenu = true
                }
                else {
                  viewModel.updateMainScreen(ShowSelection)
                  viewModel.showMenu = false
                }
              }
            }
          })
  }



  fun signOut() {
    FirebaseAuth.getInstance()?.signOut()
  }
}