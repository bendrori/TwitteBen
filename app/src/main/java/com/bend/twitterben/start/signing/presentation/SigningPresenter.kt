package com.bend.twitterben.start.signing.presentation
import com.google.firebase.auth.FirebaseAuth

class SigningPresenter(private val viewModel: SigningViewModel):
    ISigningPresenter , ISigningObservers by viewModel{

 override fun singingUser(email: String, password: String){
   FirebaseAuth.getInstance().signInWithEmailAndPassword(email,password)
       .addOnCompleteListener {
         if(it.isComplete){
           viewModel.updateSigningState(true)
           println("Login User Complete")
         }
       }
       .addOnFailureListener {
         println("Login User Fail ${it.message}")
       }
 }
}