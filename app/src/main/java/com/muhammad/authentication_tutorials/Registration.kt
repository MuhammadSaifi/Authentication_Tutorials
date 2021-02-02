package com.muhammad.authentication_tutorials

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.registration_activity.*

//implementation 'com.squareup.picasso:picasso:2.71828'

class Registration : AppCompatActivity() {
    private var firebaseAuth: FirebaseAuth? = null
    private var prg: ProgressDialog? = null
    internal lateinit var Uemail: String
    internal lateinit var name: String
    internal lateinit var age: String
    internal lateinit var pass: String
    private var PICK_IMAGE_REQUEST =12
    private var imagePath: Uri?=null
    private var firebaseStorage: FirebaseStorage?=null
    private var storageReference: StorageReference?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.registration_activity)

        firebaseAuth = FirebaseAuth.getInstance()
        firebaseStorage = FirebaseStorage.getInstance()
        storageReference= FirebaseStorage.getInstance().reference

        ivProfile.setOnClickListener {
            fileChooser()
        }

        prg = ProgressDialog(this)
        btnSignin.setOnClickListener {
            register()
        }

    }

    fun register() {
        name = etUserName!!.text.toString()
        pass = password.text.toString()
        Uemail = email!!.text.toString()
        age = etAge!!.text.toString()

        prg?.setMessage("Please Subscribe Channel")
        prg?.show()

        if (Uemail.isEmpty() || pass.isEmpty() || name.isEmpty() || age.isEmpty()) {
            Toast.makeText(this, "Fill All Fields", Toast.LENGTH_SHORT).show()
        } else {
            firebaseAuth!!.createUserWithEmailAndPassword(Uemail, pass)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        prg?.dismiss()
                        //       checkEmail()
                        sendData()
                        Toast.makeText(this, "Successfull", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this, SecondActivity::class.java))

                    } else {
                        prg?.dismiss()
                        Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
                    }
                }

        }
    }

    fun checkEmail() {
        val firebaseUser = firebaseAuth?.currentUser
        firebaseUser?.sendEmailVerification()?.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Toast.makeText(this, "Verification mail sent", Toast.LENGTH_SHORT).show()
                firebaseAuth?.signOut()
                finish()
                startActivity(Intent(this, MainActivity::class.java))
            } else {
                Toast.makeText(this, "Error Ocoured", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun sendData() {
        val firebaseDatabase = FirebaseDatabase.getInstance()
        val myReference = firebaseDatabase.getReference(firebaseAuth?.uid.toString())

       val imageRef = storageReference!!.child(firebaseAuth!!.uid!!)
           .child("image").child("Profile Pic")
        val uploadImage = imageRef.putFile(imagePath!!)
        uploadImage.addOnFailureListener{
            Toast.makeText(this,"Error Ocoured", Toast.LENGTH_SHORT).show()
        }

        val userProfile = UserProfile(age, Uemail, name)
        myReference.setValue(userProfile)
    }
private fun fileChooser(){
    val intent = Intent()
    intent.type ="image/*"
    intent.action = Intent.ACTION_GET_CONTENT
    startActivityForResult(intent, PICK_IMAGE_REQUEST)
}
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK
            &&data!=null && data.data!=null){
            imagePath = data.data
            Picasso.get().load(imagePath).into(ivProfile)
        }
    }
}