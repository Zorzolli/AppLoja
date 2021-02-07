package com.zorzolli.apploja.Fragments

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.zorzolli.apploja.Model.Data
import com.zorzolli.apploja.R
import kotlinx.android.synthetic.main.activity_register_products.*
import java.util.*

class RegisterProducts : AppCompatActivity() {

    private var SelectUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_products)

        bt_select_photo.setOnClickListener {
            SelectPhoto()
        }

        bt_register_product.setOnClickListener {
            SaveDataToFirebase()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 0) {
            SelectUri = data?.data

            val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, SelectUri)
            product_photo.setImageBitmap(bitmap)
            bt_select_photo.alpha = 0f
        }
    }

    private fun SelectPhoto() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, 0)
    }

    private fun SaveDataToFirebase() {
        val fileName = UUID.randomUUID().toString()
        val reference = FirebaseStorage.getInstance().getReference(
            "/images/${fileName}")
        SelectUri?.let {
            reference.putFile(it)
                .addOnSuccessListener {
                    reference.downloadUrl.addOnSuccessListener {
                        val url = it.toString()
                        val name = edit_name.text.toString()
                        val price = edit_price.text.toString()
                        val uid = FirebaseAuth.getInstance().uid

                        val Products = Data(url, name, price)
                        FirebaseFirestore.getInstance().collection("Products")
                            .add(Products)
                            .addOnSuccessListener {
                                Toast.makeText(this, R.string.product_successfully_registered.toString(), Toast.LENGTH_SHORT).show()
                            }.addOnFailureListener {

                            }
                    }
                }
        }
    }

}