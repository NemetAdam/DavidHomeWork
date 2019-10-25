package com.example.adammal

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.widget.Button
import androidx.annotation.RequiresApi
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var isFragmentOneLoaded = true
    val manager = supportFragmentManager


    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        chronometer.isCountDown = true
        chronometer.base = SystemClock.elapsedRealtime() + 10000
        chronometer.start()
        chronometer.setOnChronometerTickListener {
            if (it.text.toString() == "00:00") {

                it.stop()
                ShowFragmentTwo()
                //atlep az eredmenyekhez
                //majd ugyaninnen indul az uj timer
                chronometer.isCountDown = true
                chronometer.base = SystemClock.elapsedRealtime() + 20000
                chronometer.start()
                //kezeld le hogy ne lehessen visszalepni
            }

        }
        val database = FirebaseDatabase.getInstance()
        val myRef = database.reference.child("RoomId").child("25621").child("Description")
        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                val value = dataSnapshot.getValue(String::class.java)
                taskname.text = value
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
            }
        })

        //Ideiglenes gomb hogy tudd megnezni mint ket fragmentet
        //majd az atvalt magatol mikor mindenki szavazott
        val Change = this.btn_change
        ShowFragmentOne()
        Change.setOnClickListener({

            if (isFragmentOneLoaded)
                ShowFragmentTwo()
            else
                ShowFragmentOne()
        })


    }

    fun ShowFragmentOne() {
        val transaction = manager.beginTransaction()
        val fragment = FragmentOne()
        transaction.replace(R.id.fragment_holder, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
        isFragmentOneLoaded = true
    }

    fun ShowFragmentTwo() {
        val transaction = manager.beginTransaction()
        val fragment = FragmentTwo()
        transaction.replace(R.id.fragment_holder, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
        isFragmentOneLoaded = false
    }
}
