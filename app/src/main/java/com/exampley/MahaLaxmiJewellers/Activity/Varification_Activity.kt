package com.exampley.MahaLaxmiJewellers.Activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.exampley.MahaLaxmiJewellers.R
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_varification_.*


class Varification_Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_varification_)
        val varificationText = "5975edebfe142409148121118fb9f73f5975edeb"
        val edit_text = findViewById<EditText>(R.id.vari_code)

        try {
            val user = Firebase.auth.currentUser
            Log.d("ERROR", "user is ${user.toString()}")

            if (user != null) {
                // User is signed in.
                Toast.makeText(this, "You Are Already Log in", Toast.LENGTH_SHORT).show()

            } else {
                // No user is signed in.
                vari_btn.setOnClickListener {
                    Log.d("ERROR", "$varificationText == ${edit_text.toString()}")
                    if (edit_text.text.toString() == varificationText) {
                        startActivity(Intent(this, LoginActivity::class.java))
                        finish()
                    } else {

                        Toast.makeText(this, "Please Enter Valid Code", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        } catch (e: Throwable) {
            Log.d("ERROR", "Error is $e")
        }


    }
}