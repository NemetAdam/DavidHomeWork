package com.example.adammal

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import kotlinx.android.synthetic.main.activity_login.*

import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.widget.Toast
import com.google.firebase.FirebaseError
import com.google.firebase.database.*
import com.google.firebase.database.FirebaseDatabase
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import com.example.adammal.Global.userName


class LoginActivity : AppCompatActivity() {


    var btn_Login : Button? = null

    var room:String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        btn_Login = this.btn_login

        val database = FirebaseDatabase.getInstance()
        val myRef = database.reference
        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                val value = dataSnapshot.child("Room").getValue(String::class.java)
                room = value
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
            }
        })

        btn_Login!!.setOnClickListener(object : View.OnClickListener{
            override fun onClick(view: View){
                val intent = Intent(this@LoginActivity, MainActivity::class.java)
                startActivity(intent)
                if (!edt_username.text.isEmpty()){
                    if (edt_roomid.text.toString()==room){
                        val userReference = database.getReference()
                        userReference.child("Users").push().setValue(edt_username.text.toString())
                        userName = edt_username.text.toString()
                        val intent = Intent(this@LoginActivity, MainActivity::class.java)
                        startActivity(intent)
                    } else {
                        Toast.makeText(applicationContext,"Incorrect room number",Toast.LENGTH_LONG).show()
                    }
                } else {
                    Toast.makeText(applicationContext,"Username is missing",Toast.LENGTH_LONG).show()
                }
            }
        })



    }




}
