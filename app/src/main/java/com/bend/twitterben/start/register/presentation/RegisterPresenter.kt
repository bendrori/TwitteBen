package com.bend.twitterben.start.register.presentation

import android.net.Uri
import android.support.v7.app.AlertDialog
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.database.FirebaseDatabase



class RegisterPresenter(private val viewModel: RegisterViewModel):
    IRegisterPresenter , IRegisterObservers by viewModel{


 override fun registerUser(name: String,email: String, password: String, url: String) {
     var auth = FirebaseAuth.getInstance()


     auth.createUserWithEmailAndPassword(email,password)
       .addOnCompleteListener { task ->
         if(task.isSuccessful){
           println("Register User Complete")
           val user = auth.currentUser
           val profileUpdates = UserProfileChangeRequest.Builder()
               .setDisplayName(name)
               .setPhotoUri(Uri.parse(url))
               .build()

           user?.updateProfile(profileUpdates)?.addOnCompleteListener {
             addUserToDB(User(user.uid,name,user.email!!,urlImage = url))
             viewModel.updateRegisterState(true)
           }
         }
           if(task.isCanceled)
               task.exception.toString()
       }
       .addOnFailureListener {
           viewModel.updateRegisterState(false)
       }
 }

  private fun addUserToDB(user: User) {
    val database = FirebaseDatabase.getInstance()
    val myRef = database.getReference("Users").child(user.uuid)
    myRef.setValue(user)
  }
}