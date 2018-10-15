package com.bend.twitterben.start.register.presentation

import android.net.Uri
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.database.FirebaseDatabase



class RegisterPresenter(private val viewModel: RegisterViewModel):
    IRegisterPresenter , IRegisterObservers by viewModel{

 override fun registerUser(name: String,email: String, password: String, url: String) {
   FirebaseAuth.getInstance()
       .createUserWithEmailAndPassword(email,password)
       .addOnCompleteListener { task ->
         if(task.isComplete){
           println("Register User Complete")
           val user = FirebaseAuth.getInstance().currentUser
           val profileUpdates = UserProfileChangeRequest.Builder()
               .setDisplayName(name)
               .setPhotoUri(Uri.parse(url))
               .build()

           user?.updateProfile(profileUpdates)?.addOnCompleteListener {
             addUserToDB(User(user.uid,name,user.email!!,urlImage = url))
             viewModel.updateRegisterState(true)
           }
         }
       }
       .addOnFailureListener {
         println("Register User Fail ${it.message}")
       }
 }

  private fun addUserToDB(user: User) {
    val database = FirebaseDatabase.getInstance()
    val myRef = database.getReference("Users").child(user.uuid)
    myRef.setValue(user)
  }
}