package com.bend.twitterben.start.register.view

import android.app.Activity
import android.app.ProgressDialog.show
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ProgressBar
import com.bend.twitterben.R
import com.bend.twitterben.start.main.view.MainActivity
import com.bend.twitterben.start.register.presentation.RegisterPresenter
import com.bend.twitterben.start.register.presentation.RegisterViewModel
import kotlinx.android.synthetic.main.activity_register.addImageButtonRegisterId
import kotlinx.android.synthetic.main.activity_register.emailEditTextRegisterId
import kotlinx.android.synthetic.main.activity_register.my_toolbar
import kotlinx.android.synthetic.main.activity_register.nameEditTextRegisterId
import kotlinx.android.synthetic.main.activity_register.nextButtonRegisterId
import kotlinx.android.synthetic.main.activity_register.passwordEditTextRegisterId
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_register.activity_registerId
import kotlinx.android.synthetic.main.activity_register.imageProgressBarId
import java.util.UUID

class RegisterActivity : AppCompatActivity() {

  private val viewModel = RegisterViewModel()
  private var presenter = RegisterPresenter(viewModel)
  private var url: String = ""

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_register)

    setSupportActionBar(my_toolbar)
    supportActionBar?.setDisplayHomeAsUpEnabled(true)
    supportActionBar?.setDisplayShowTitleEnabled(false)

    nextButtonRegisterId.setOnClickListener {
      registerUser()
    }

    addImageButtonRegisterId.setOnClickListener {
      val intent = Intent().apply {
        type = "image/*"
        action = Intent.ACTION_GET_CONTENT
      }
      startActivityForResult(Intent.createChooser(intent, "Select Picture"), 1)
    }

    presenter.registerStateObserve(lifecycle) {
      if(it!!) {
      val intent = Intent(this, MainActivity::class.java)
      startActivity(intent)
      }
    }
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    return when (item.itemId) {
      android.R.id.home -> {
        super.onBackPressed()
        true
      }
      else -> super.onOptionsItemSelected(item)
    }
  }

  private fun registerUser() {
    val name = nameEditTextRegisterId.text.toString()
    val email = emailEditTextRegisterId.text.toString().trim()
    val password = passwordEditTextRegisterId.text.toString()
    if(name.isNotBlank() && email.isNotBlank() && password.isNotBlank()) {
     println("registerUser URL $url")
      presenter.registerUser(name, email, password,url)
    }
  }

  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    super.onActivityResult(requestCode, resultCode, data)
    if(resultCode != Activity.RESULT_OK)
      return
    if(requestCode == 1) {
      val path = data?.data
      if (path != null) {
       val ref =  FirebaseStorage.getInstance().reference.child("images/${UUID.randomUUID()}")
        ref.putFile(path)
            .addOnProgressListener {
              imageProgressBarId.progress = ((100.0 * it.bytesTransferred) / it.totalByteCount).toInt()
              nextButtonRegisterId.isClickable = false
              imageProgressBarId.visibility = View.VISIBLE
            }
            .addOnSuccessListener{
              ref.downloadUrl.addOnSuccessListener {uri ->
                url = uri.toString()
              }
              imageProgressBarId.visibility = View.GONE
              addImageButtonRegisterId.text = "change image?"
              nextButtonRegisterId.isClickable = true
            }
            .addOnFailureListener {
              println("On Failure Listener putFile ${it.message} ")
              imageProgressBarId.visibility = View.GONE
            }
      }
    }
  }
}
