package com.bend.twitterben.start.firebase

import com.bend.twitterben.start.register.presentation.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.util.ArrayList

class UsersRepository {

  var firebaseDatabaseReference =  FirebaseDatabase.getInstance().reference
  val currentUser = FirebaseAuth.getInstance().currentUser
  private lateinit var ridesHandler: (MutableList<User>?) -> Unit
  private lateinit var followingHandler: (MutableList<User>?) -> Unit
  private val list: MutableList<User> = mutableListOf()

  private val listener = object : ValueEventListener {
    override fun onDataChange(dataSnapshot: DataSnapshot) {
      ridesHandler(parseData(dataSnapshot))
    }

    override fun onCancelled(databaseError: DatabaseError) {
    }
  }

  private val followinglistener = object : ValueEventListener {
    override fun onDataChange(dataSnapshot: DataSnapshot) {
      followingHandler(parseData(dataSnapshot))
    }

    override fun onCancelled(databaseError: DatabaseError) {
    }
  }

  fun startListener(ridesHandler: (MutableList<User>?) -> Unit) {
    this.ridesHandler = ridesHandler
    firebaseDatabaseReference.child("Users").addValueEventListener(listener)
  }

  fun stopListener() {
    firebaseDatabaseReference.child("Users").removeEventListener(listener)
  }

  fun startFolloingListener(ridesHandler: (MutableList<User>?) -> Unit) {
    this.followingHandler = ridesHandler
    firebaseDatabaseReference
        .child("Users/${currentUser?.uid!!}/following")
        .addValueEventListener(followinglistener)
  }

  fun stopFolloingListener() {
    firebaseDatabaseReference
        .child("Users/${currentUser?.uid!!}/following")
        .removeEventListener(followinglistener)
  }

  fun parseData(dataSnapshot: DataSnapshot) : MutableList<User> {
    list.clear()
    dataSnapshot.children.mapNotNullTo(list) {
      it.getValue<User>(User::class.java)
    }
    return list
  }

  fun updateUser(path: String ,value: Any) {
    if(currentUser != null)
      firebaseDatabaseReference
          .child("Users")
          .child(path)
          .setValue(value)
          .addOnSuccessListener {
            println("Update Current User Success")
          }
          .addOnFailureListener {
            println("Update Current User Failure ${it.message}")
          }
  }

  fun removeValue(path: String) {
    if(currentUser != null)
      firebaseDatabaseReference
          .child("Users")
          .child(path).removeValue()
          .addOnSuccessListener {
            println("Remove Value User Success")
          }
          .addOnFailureListener {
            println("Remove Value User Failure ${it.message}")
          }
  }

  fun updateFollowingNumber() {
    firebaseDatabaseReference
        .child("Users/${currentUser?.uid}/following")
        .addListenerForSingleValueEvent(object : ValueEventListener {
          override fun onCancelled(databaseError: DatabaseError) {}
          override fun onDataChange(dataSnapshot: DataSnapshot) {
            val list: ArrayList<User> = arrayListOf()
            dataSnapshot.children.mapNotNullTo(list) { it.getValue<User>(User::class.java) }
            updateUser("${currentUser?.uid}/followingNumber",(list.size))
          }
        })
  }

  fun updateFollowersNumber() {
    firebaseDatabaseReference
        .child("Users/${currentUser?.uid}/followers")
        .addListenerForSingleValueEvent(object : ValueEventListener {
          override fun onCancelled(databaseError: DatabaseError) {}
          override fun onDataChange(dataSnapshot: DataSnapshot) {
            val list: ArrayList<User> = arrayListOf()
            dataSnapshot.children.mapNotNullTo(list) { it.getValue<User>(User::class.java) }
            updateUser("${currentUser?.uid}/followersNumber",(list.size))
          }
        })
  }



}