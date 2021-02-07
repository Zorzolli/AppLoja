package com.zorzolli.apploja.Form

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.zorzolli.apploja.MainScreen
import com.zorzolli.apploja.R
import kotlinx.android.synthetic.main.activity_form_login.*

class FormLogin : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form_login)

        CheckLoggedUser()

        supportActionBar!!.hide()

        text_register_screen.setOnClickListener {
            var intent = Intent(this, FormRegister::class.java)
            startActivity(intent)
        }

        bt_login.setOnClickListener {
            AuthenticateUser()
        }
    }

    private fun AuthenticateUser() {

        val email = edit_email.text.toString()
        val password = edit_password.text.toString()

        if (email.isEmpty() || password.isEmpty()) {
            var snackbar = Snackbar.make(layout_login, R.string.enter_an_email_and_password.toString(), Snackbar.LENGTH_INDEFINITE).setBackgroundTint(
                Color.WHITE).setTextColor(Color.BLACK)
                .setAction("Ok", View.OnClickListener {

                })
            snackbar.show()
        } else {
            FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password).addOnCompleteListener {
                if (it.isSuccessful) {
                    frameL.visibility = View.VISIBLE
                    Handler().postDelayed({OpenMainScreen()}, 3000)

                }
            }.addOnFailureListener {
                var snackbar = Snackbar.make(layout_login, R.string.error_login_user.toString(), Snackbar.LENGTH_INDEFINITE).setBackgroundTint(
                    Color.WHITE).setTextColor(Color.BLACK)
                    .setAction("Ok", View.OnClickListener {

                    })
                snackbar.show()
            }
        }
    }

    private fun CheckLoggedUser() {

        val currentUser = FirebaseAuth.getInstance().currentUser
        if (currentUser != null) {
            OpenMainScreen()
        }
    }

    private fun OpenMainScreen() {
        var intent = Intent(this, MainScreen::class.java)
        startActivity(intent)
        finish()
    }
}