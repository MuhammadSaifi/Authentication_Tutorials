package com.muhammad.authentication_tutorials

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.second_activity.*

class SecondActivity : AppCompatActivity() {
    private var firebaseAuth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.second_activity)

        title = "Second Activity"
        firebaseAuth = FirebaseAuth.getInstance()
        btnLogout.setOnClickListener {
            firebaseAuth?.signOut()
            startActivity(Intent(this, MainActivity::class.java))
        }

        btnProfile.setOnClickListener {
            startActivity(Intent(this,Profile::class.java))
        }

        btnUpdate.setOnClickListener {
            startActivity(Intent(this,UpdateProfile::class.java))
        }

    }
}