package com.bend.twitterben.start.signing.view

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.bend.twitterben.R
import android.view.Menu
import android.view.MenuItem
import com.bend.twitterben.start.main.view.MainActivity
import com.bend.twitterben.start.signing.presentation.SigningPresenter
import com.bend.twitterben.start.register.view.RegisterActivity
import com.bend.twitterben.start.signing.presentation.SigningViewModel
import kotlinx.android.synthetic.main.activity_login.emailEditTextLoginId
import kotlinx.android.synthetic.main.activity_login.loginButtonLoginId
import kotlinx.android.synthetic.main.activity_login.my_toolbar
import kotlinx.android.synthetic.main.activity_login.passwordEditTextLoginId

class SigningActivity : AppCompatActivity() {

  private var presenter = SigningPresenter(SigningViewModel())

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_login)

    setSupportActionBar(my_toolbar)
    supportActionBar?.setDisplayShowTitleEnabled(false)

    loginButtonLoginId.setOnClickListener {
      loginUser()
    }
    presenter.signingStateObserve(lifecycle) {
      if(it!!) {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
      }
    }
  }

  override fun onCreateOptionsMenu(menu: Menu): Boolean {
    menuInflater.inflate(R.menu.main_menu, menu)
    return true
  }

  override fun onOptionsItemSelected(p0: MenuItem): Boolean {
    when (p0.itemId) {
      R.id.sign_in_ic -> {
        val intent = Intent(this, RegisterActivity::class.java)
        startActivity(intent)
        finish()
      }
    }
    return true
  }

  private fun loginUser() {
    val email = emailEditTextLoginId.text.toString()
    val password = passwordEditTextLoginId.text.toString().trim()
    if(email.isNotBlank() && password.isNotBlank())
      presenter.singingUser(email,password)

  }
}
