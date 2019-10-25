package com.example.adammal


import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.adammal.UserName.userName
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.NonCancellable.children

/**
 * Created by VickY on 07-09-2017.
 */
class FragmentTwo : Fragment(){

    val TAG = "FragmentTwo"


    private var mRecyclerView: RecyclerView? = null
    private var mAdapter: RecyclerView.Adapter<*>? = null

    override fun onAttach(context: Context) {
        Log.d(TAG, "onAttach")
        super.onAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(TAG, "onCreate")
        super.onCreate(savedInstanceState)
    }

    fun loadWithData() : ArrayList<User>{
        val users = ArrayList<User>()
        val database = FirebaseDatabase.getInstance()
        val myRef = database.reference.child("UserVote")
        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (ds in dataSnapshot.children) {
                    val nameVoter = ds.key.toString()
                    val voteName = ds.value.toString()

                    users.add(User(nameVoter,voteName))
                    mAdapter?.notifyDataSetChanged()
                }
            }
            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
            }
        })
        return users
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Log.d(TAG, "onCreateView")
        //return inflater!!.inflate(R.layout.fragment_two,container,false)

        val rootView = inflater.inflate(R.layout.fragment_two,container,false)

        mRecyclerView = rootView.findViewById(R.id.rvItems) as RecyclerView
        mRecyclerView!!.layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.VERTICAL, false)

        var votesList : ArrayList<User> = ArrayList()



        // Csak a teszt miatt toltok itt bele adatokat
        votesList= loadWithData()

        mAdapter = SummaryAdapter(votesList)
        mRecyclerView!!.adapter = mAdapter


        // teszt vege

        return rootView
    }



    override fun onActivityCreated(savedInstanceState: Bundle?) {
        Log.d(TAG, "onActivityCreated")
        super.onActivityCreated(savedInstanceState)
    }

    override fun onStart() {
        Log.d(TAG, "onStart")
        super.onStart()
    }

    override fun onResume() {
        Log.d(TAG, "onResume")
        super.onResume()
    }

    override fun onPause() {
        Log.d(TAG, "onPause")
        super.onPause()
    }

    override fun onStop() {
        Log.d(TAG, "onStop")
        super.onStop()
    }

    override fun onDestroyView() {
        Log.d(TAG, "onDestroyView")
        super.onDestroyView()
    }

    override fun onDestroy() {
        Log.d(TAG, "onDestroy")
        super.onDestroy()
    }

    override fun onDetach() {
        Log.d(TAG, "onDetach")
        super.onDetach()
    }
}