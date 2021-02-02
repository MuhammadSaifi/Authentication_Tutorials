package com.muhammad.authentication_tutorials

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_update.*
import kotlinx.android.synthetic.main.registration_activity.*

class UpdateProfile : AppCompatActivity() {
    private var newUserAge: EditText? =null
    private var newUserName: EditText?=null
    private var newUserEmail: EditText?=null
    private var firebaseAuth: FirebaseAuth? = null
    private var PICK_IMAGE_REQUEST =12
    private var imagePath: Uri?=null
    private var firebaseStorage: FirebaseStorage?=null
    private var storageReference: StorageReference?=null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update)

        newUserName = findViewById(R.id.etNameUpdate)
        newUserAge = findViewById(R.id.etAgeUpdate)
        newUserEmail = findViewById(R.id.etEmailUpdate)

        firebaseStorage = FirebaseStorage.getInstance()
        storageReference= FirebaseStorage.getInstance().reference

        updatePic.setOnClickListener {
            fileChooser()
        }


        firebaseAuth = FirebaseAuth.getInstance()
        val databaseReference = FirebaseDatabase.getInstance().getReference(firebaseAuth!!.uid!!)
         btnSave.setOnClickListener {
             val age = newUserAge?.text.toString()
             val name = newUserName?.text.toString()
             val email = newUserEmail?.text.toString()

             if(age.isEmpty() || name.isEmpty() || email.isEmpty()){
                 Toast.makeText(this,"Fill all field", Toast.LENGTH_SHORT).show()
             }else{
                 val userProfile = UserProfile(age,email,name)
                 databaseReference.setValue(userProfile)

                 val imageRef = storageReference!!.child(firebaseAuth!!.uid!!)
                     .child("image").child("Profile Pic")
                 val uploadImage = imageRef.putFile(imagePath!!)
                 uploadImage.addOnFailureListener{
                     Toast.makeText(this,"Error Ocoured", Toast.LENGTH_SHORT).show()
                 }

             }
         }

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
            Picasso.get().load(imagePath).into(updatePic)
        }
    }
}
