package com.muhammad.authentication_tutorials

import android.net.Uri
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_profie.*
import kotlinx.android.synthetic.main.registration_activity.*

class Profile : AppCompatActivity() {

    var firebaseAuth: FirebaseAuth?=null
    lateinit var profileAge: TextView
    lateinit var profileEmail: TextView
    lateinit var profileName: TextView
    private var firebaseStorage: FirebaseStorage?=null
    private var storageReference: StorageReference?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profie)

        profileAge = findViewById(R.id.tvProfileAge)
        profileEmail = findViewById(R.id.tvProfileEmail)
        profileName = findViewById(R.id.tvProfileName)

        firebaseAuth = FirebaseAuth.getInstance()
        firebaseStorage= FirebaseStorage.getInstance()
        storageReference = firebaseStorage!!.reference

        storageReference!!.child(firebaseAuth!!.uid!!).child("image/Profile Pic")
            .downloadUrl.addOnSuccessListener { uri ->
            Picasso.get().load(uri).into(profilePic)
        }


        val databaseReference = FirebaseDatabase.getInstance().getReference(firebaseAuth!!.uid!!)
        databaseReference.addValueEventListener(object : ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {
                Toast.makeText(this@Profile, p0.code, Toast.LENGTH_SHORT).show()
            }

            override fun onDataChange(p0: DataSnapshot) {
                val userProfile = p0.getValue(UserProfile::class.java)
                profileAge.text ="Age:  " + userProfile?.userAge
                profileEmail.text = "Email:  " + userProfile?.userEmail
                profileName.text = "Name:  " + userProfile?.userName
            }
        })

    }
}

