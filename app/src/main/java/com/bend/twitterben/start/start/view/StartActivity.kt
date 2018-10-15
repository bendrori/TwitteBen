package com.bend.twitterben.start.start.view

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.bend.twitterben.R.layout
import com.bend.twitterben.start.main.view.MainActivity
import com.bend.twitterben.start.signing.view.SigningActivity
import com.bend.twitterben.start.register.view.RegisterActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_start.getStartButtonId
import kotlinx.android.synthetic.main.activity_start.loginTextViewId

class StartActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(layout.activity_start)

    if(FirebaseAuth.getInstance().currentUser != null)
      skipStart()

    getStartButtonId.setOnClickListener {
      goToRegister()
    }

    loginTextViewId.setOnClickListener {
      goToLogin()
    }

  }

  private fun goToRegister() {
    val intent = Intent(this, RegisterActivity::class.java)
    startActivity(intent)
  }

  private fun goToLogin() {
    val intent = Intent(this, SigningActivity::class.java)
    startActivity(intent)
  }

  private fun skipStart() {
    val intent = Intent(this, MainActivity::class.java)
    startActivity(intent)
  }
}
