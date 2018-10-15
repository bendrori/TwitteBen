package com.bend.twitterben.start.firebase

import com.bend.twitterben.start.home.view.Twitte
import com.bend.twitterben.start.register.presentation.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.util.UUID
import com.google.firebase.FirebaseError
import java.util.ArrayList

class TweetsRepository {

  var firebaseDatabaseReference =  FirebaseDatabase.getInstance().reference
  val currentUser = FirebaseAuth.getInstance().currentUser
  private lateinit var tweetsHandler: (MutableList<Twitte>?) -> Unit

  private val list: MutableList<Twitte> = mutableListOf()

  private val listener = object : ValueEventListener {
    override fun onDataChange(dataSnapshot: DataSnapshot) {
      tweetsHandler(parseData(dataSnapshot))
    }

    override fun onCancelled(databaseError: DatabaseError) {
    }
  }

  fun startListener( ridesHandler: (MutableList<Twitte>?) -> Unit) {
    this.tweetsHandler = ridesHandler
    firebaseDatabaseReference.child("Twittes").addValueEventListener(listener)
  }

  fun stopListener() {
    firebaseDatabaseReference.child("Twittes").removeEventListener(listener)
  }

  fun parseData(dataSnapshot: DataSnapshot) : MutableList<Twitte> {
    list.clear()
    dataSnapshot.children.mapNotNullTo(list) {
      it.getValue<Twitte>(Twitte::class.java)
    }
    return list
  }

  fun writeTweet(tweet: String) {
   val tweetId = UUID.randomUUID().toString()
    firebaseDatabaseReference.child("Twittes/$tweetId")
        .setValue(
            Twitte(
                id = tweetId,
                title = currentUser?.email!!,
                urlPic = currentUser.photoUrl.toString(),
                text =  tweet,
                time =  System.currentTimeMillis().toString(),
                userUuid = currentUser.uid))
        .addOnSuccessListener {
          println("Twitte Was Send Success")
        }.addOnFailureListener {
          println("Twitte Was Not Send ${it.message}")
        }
  }

  fun addLikeToTweet(path: String) {
    firebaseDatabaseReference
        .child("Twittes")
        .child(path)
        .child("likes").addListenerForSingleValueEvent(object : ValueEventListener {
          override fun onCancelled(p0: DatabaseError) {
            println("DatabaseError ${p0.message}")
          }
          override fun onDataChange(dataSnapshot: DataSnapshot) {
            var value =   dataSnapshot.value.toString().toInt()
              value += 1
            dataSnapshot.ref.setValue(value.toString())
                .addOnSuccessListener {
                        println("like Was Add Success")
        }.addOnFailureListener {
          println("like Was Not Add ${it.message}")
        }
      }
    })
  }

  fun userLikeTheTwitte(path: String) {
    if (currentUser != null) {
      firebaseDatabaseReference
          .child("Twittes")
          .child(path)
          .child("likesUsers")
          .child(currentUser.uid)
          .setValue(User(name = currentUser.displayName!!))
          .addOnSuccessListener {
            updateTwitteLikes(path)
          }
    }
  }

  fun userDontLikeTheTwitte(path: String) {
    if (currentUser != null) {
      firebaseDatabaseReference
          .child("Twittes")
          .child(path)
          .child("likesUsers")
          .child(currentUser.uid).removeValue()
          .addOnSuccessListener {
            updateTwitteLikes(path)
          }.addOnFailureListener {
            println("like Was Not Add ${it.message}")
          }
    }
  }

  private fun updateTwitteLikes(path: String) {
    firebaseDatabaseReference
        .child("Twittes/$path/likesUsers")
        .addListenerForSingleValueEvent(object : ValueEventListener {
          override fun onCancelled(databaseError: DatabaseError) {}
          override fun onDataChange(dataSnapshot: DataSnapshot) {
            val list: ArrayList<User> = arrayListOf()
            dataSnapshot.children.mapNotNullTo(list) { it.getValue<User>(User::class.java) }
            firebaseDatabaseReference.child("Twittes/$path/likes")
                .setValue(list.size.toString())
          }
        })
  }

//  fun monitorLikeForUser() {
//    firebaseDatabaseReference
//        .child("Twittes/$path/likesUsers")
//        .addListenerForSingleValueEvent(object : ValueEventListener {
//          override fun onCancelled(databaseError: DatabaseError) {}
//          override fun onDataChange(dataSnapshot: DataSnapshot) {
//            val list: ArrayList<User> = arrayListOf()
//            dataSnapshot.children.mapNotNullTo(list) { it.getValue<User>(User::class.java) }
//            firebaseDatabaseReference.child("Twittes/$path/likes")
//                .setValue(list.size.toString())
//          }
//        })
//  }
}