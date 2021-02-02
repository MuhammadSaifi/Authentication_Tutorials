package com.muhammad.authentication_tutorials

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*

/*Connect Project With Firebase.  <Done>
* Login User                   <Done>
* Sign Up User
* Forgot Password
* Store User Information
* Display User Information
* Update User Information
* Firebase Storage*/

class MainActivity : AppCompatActivity() {
    private var Email: EditText? = null
    private var password: EditText? = null
    private var firebaseAuth: FirebaseAuth? = null
    private var prg: ProgressDialog? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Email = findViewById(R.id.etEmail)
        password = findViewById(R.id.etPassword)

        firebaseAuth = FirebaseAuth.getInstance()

        prg = ProgressDialog(this)

        btnLogin.setOnClickListener {
            validate()
        }

        val user = firebaseAuth?.currentUser
        if (user != null) {
            startActivity(Intent(this, SecondActivity::class.java))

        }

tvForgotPassword.setOnClickListener {
    startActivity(Intent(this,PasswordRe::class.java))
}
        register.setOnClickListener {
            startActivity(Intent(this,Registration::class.java))
        }


    }

    fun validate() {
        var email = Email?.text.toString()
        var password = password?.text.toString()
        prg?.setMessage("Please Subscribe Channel")
        prg?.show()
        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Fill all Fields", Toast.LENGTH_SHORT).show()
        } else {
            firebaseAuth!!.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        prg?.dismiss()
                     //   verifyemail()
                        Toast.makeText(this,"Successfull",Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this,SecondActivity::class.java))
                    } else {
                        prg?.dismiss()
                        Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }

    /*fun verifyemail(){
        val firebaseUser = FirebaseAuth.getInstance().currentUser
        val vemail = firebaseUser?.isEmailVerified

       startActivity(Intent(this,SecondActivity::class.java))
       /* if(emailflag){
            finish();
            startActivity(new Intent(MainActivity.this, SecondActivity.class));
        }else{
            Toast.makeText(this, "Verify your email", Toast.LENGTH_SHORT).show();
            firebaseAuth.signOut();
      }*/
    }
*/

}
