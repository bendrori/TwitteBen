package com.bend.twitterben.start.firebase

import com.bend.twitterben.start.messagesroom.view.Message
import com.bend.twitterben.start.register.presentation.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MessagesRepository {

  var firebaseDatabaseReference =  FirebaseDatabase.getInstance().reference
  private val currentUser = FirebaseAuth.getInstance().currentUser
  private lateinit var messagesHandler: (MutableList<Message>?) -> Unit
  private lateinit var lastMessagesHandler: (MutableList<Message>?) -> Unit

  private val messagesList: MutableList<Message> = mutableListOf()
  private val lastMessagesList: MutableList<Message> = mutableListOf()

  private val listener = object : ValueEventListener {
    override fun onDataChange(dataSnapshot: DataSnapshot) {
      messagesHandler(parseData(dataSnapshot))
    }

    override fun onCancelled(databaseError: DatabaseError) {
    }
  }

  private val lastMessagesListener = object : ValueEventListener {
    override fun onDataChange(dataSnapshot: DataSnapshot) {
      lastMessagesHandler(parseLastData(dataSnapshot))
    }

    override fun onCancelled(databaseError: DatabaseError) {
    }
  }

  fun startListenerToLastMessages(lastMessagesHandler: (MutableList<Message>?) -> Unit) {
    this.lastMessagesHandler = lastMessagesHandler
    firebaseDatabaseReference.child("last-message/${currentUser?.uid}")
        .addValueEventListener(lastMessagesListener)
  }
  fun startListener(toId: String, messagesHandler: (MutableList<Message>?) -> Unit) {
    this.messagesHandler = messagesHandler
    firebaseDatabaseReference.child("user-messages/${currentUser?.uid}/$toId").addValueEventListener(listener)
  }

  fun stopListener() {
    firebaseDatabaseReference.child("user-messages").removeEventListener(listener)
  }

  fun parseData(dataSnapshot: DataSnapshot) : MutableList<Message> {
    messagesList.clear()
    dataSnapshot.children.mapNotNullTo(messagesList) {
      it.getValue<Message>(Message::class.java)
    }
    messagesList.forEach {
      it.isTo = it.fromId != currentUser?.uid!!
    }
    return messagesList
  }

  fun parseLastData(dataSnapshot: DataSnapshot) : MutableList<Message> {
    lastMessagesList.clear()
    dataSnapshot.children.mapNotNullTo(lastMessagesList) {
      it.getValue<Message>(Message::class.java)
    }
    return lastMessagesList
  }

  fun writeMessage(messageText: String,toId: String) {
    FirebaseDatabase.getInstance().reference.child("Users").child(toId).addListenerForSingleValueEvent(
        object : ValueEventListener {
          override fun onCancelled(p0: DatabaseError) {}

          override fun onDataChange(p0: DataSnapshot) {
            val user =  p0.getValue(User::class.java)

            val message = Message(user?.name!!,messageText, currentUser?.uid!!, toId,false ,urlImage = currentUser.photoUrl.toString(), urlImageForLast = user.urlImage)
            val messageTwo = Message(currentUser.displayName!!,messageText, toId, currentUser.uid,false, urlImage = user.urlImage,urlImageForLast = currentUser.photoUrl.toString())

            firebaseDatabaseReference.child("user-messages/${currentUser.uid}/${user.uuid}").push()
                .setValue(message)
                .addOnSuccessListener {
                  println("Message Was Send Success")
                }.addOnFailureListener {
                  println("Message Was Not Send ${it.message}")
                }

            firebaseDatabaseReference.child("user-messages/${user.uuid}/${currentUser.uid}").push()
                .setValue(message)
                .addOnSuccessListener {
                  println("Message Was Send Success")
                }.addOnFailureListener {
                  println("Message Was Not Send ${it.message}")
                }

            firebaseDatabaseReference.child("last-message/${currentUser.uid}/${user.uuid}")
                .setValue(message)
                .addOnSuccessListener {
                  println("update last-message Success")
                }.addOnFailureListener {
                  println("update last-message Failure ${it.message}")
                }

            firebaseDatabaseReference.child("last-message/${user.uuid}/${currentUser.uid}")
                .setValue(messageTwo)
                .addOnSuccessListener {
                  println("update last-message Success")
                }.addOnFailureListener {
                  println("update last-message Failure ${it.message}")
                }
          }

        }
    )
  }
}