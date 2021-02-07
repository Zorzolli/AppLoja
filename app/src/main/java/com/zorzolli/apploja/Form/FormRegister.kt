package com.zorzolli.apploja.Form

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.zorzolli.apploja.R
import kotlinx.android.synthetic.main.activity_form_register.*

class FormRegister : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form_register)

        supportActionBar!!.hide()

        bt_register.setOnClickListener {
            mRegisterUser()
        }

    }

    private fun mRegisterUser() {

        val email = edit_email.text.toString()
        val password = edit_password.text.toString()

        if (email.isEmpty() || password.isEmpty()) {
            var snackbar = Snackbar.make(layout_register, R.string.enter_your_email_and_password.toString(), Snackbar.LENGTH_INDEFINITE)
                .setBackgroundTint(Color.WHITE).setTextColor(Color.BLACK).setAction("Ok", View.OnClickListener {
                })
            snackbar.show()

        } else {

            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener {

                    if (it.isSuccessful) {
                        var snackbar = Snackbar.make(layout_register, R.string.successful_registration.toString(), Snackbar.LENGTH_INDEFINITE)
                            .setBackgroundTint(Color.WHITE).setTextColor(Color.BLACK).setAction("Ok", View.OnClickListener {
                                BackFormLogin()
                            })
                        snackbar.show()
                    }
                }.addOnFailureListener {

                    var snackbar = Snackbar.make(layout_register, R.string.error_when_registering_user.toString(), Snackbar.LENGTH_INDEFINITE)
                        .setBackgroundTint(Color.WHITE).setTextColor(Color.BLACK).setAction("Ok", View.OnClickListener {
                        })
                    snackbar.show()
                }
        }
    }

    private fun BackFormLogin() {
        var intent = Intent(this, FormLogin::class.java)
        startActivity(intent)
        finish()
    }

}