package com.muhammad.authentication_tutorials

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.resetpass_activity.*

class PasswordRe : AppCompatActivity(){
private var firebaseAuth: FirebaseAuth?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.resetpass_activity)
          firebaseAuth = FirebaseAuth.getInstance()
        btnReset.setOnClickListener {
            reset()
        }

    }
    fun reset() {
        var emailR = resetEmail.text.toString()
        if (emailR.isEmpty()) {
            Toast.makeText(this, "Please Enter Your Email", Toast.LENGTH_SHORT).show()
        }
        firebaseAuth?.sendPasswordResetEmail(emailR)?.addOnCompleteListener{task ->
            if(task.isSuccessful){
                Toast.makeText(this,"Reset Email Sent",Toast.LENGTH_SHORT).show()
                startActivity(Intent(this,MainActivity::class.java))
            }
            else{
                Toast.makeText(this,"Reset Email not Sent",Toast.LENGTH_SHORT).show()
            }
        }
    }
}