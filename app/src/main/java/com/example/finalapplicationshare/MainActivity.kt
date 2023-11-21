package com.example.finalapplicationshare

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MainActivity : AppCompatActivity() {

    private lateinit var addUsernameEditText: EditText
    private lateinit var addPasswordEditText: EditText
    private lateinit var btnAddTest: Button

    private lateinit var showAddUsernameEditText: TextView
    private lateinit var showAddPasswordEditText: TextView
    private lateinit var btnShowTest: Button

    private var firebaseDatabase: FirebaseDatabase = FirebaseDatabase.getInstance()
    private var databaseReference: DatabaseReference = firebaseDatabase.reference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        addUsernameEditText = findViewById(R.id.addUsername)
        addPasswordEditText = findViewById(R.id.addPassword)
        btnAddTest = findViewById(R.id.btnAddTest)

        showAddUsernameEditText = findViewById(R.id.showAddUsername)
        showAddPasswordEditText = findViewById(R.id.ShowAddPassword)

        showAddUsernameEditText = findViewById(R.id.showAddUsername)
        showAddPasswordEditText = findViewById(R.id.ShowAddPassword)
        btnShowTest = findViewById(R.id.btnShowTest)


        firebaseDatabase = FirebaseDatabase.getInstance()
        databaseReference = firebaseDatabase.getReference("Users")

        btnAddTest.setOnClickListener {
            val username = addUsernameEditText.text.toString()
            val password  =addPasswordEditText.text.toString()
            addValue(username, password)
        }

        btnShowTest.setOnClickListener {
            val username = addUsernameEditText.text.toString()
            val password  =addPasswordEditText.text.toString()
            showValue(username)
        }
    }

    private fun showValue(username: String) {
        databaseReference.child(username).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                        val user = snapshot.getValue(User::class.java)
                        user?.let {
                            val name = it.name
                            val password = it.password

                            if (name != null && password != null) {
                                showAddUsernameEditText.text = name
                                showAddPasswordEditText.text = password
                            }
                        }
                }
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })
    }


    private fun addValue(username: String, password: String) {
        val user = User(username, password)
        databaseReference.child(username).setValue(user)
    }


    data class User(
        val name: String = "",
        val password: String = ""
    ) {

        override fun toString(): String {
            return "User(name='$name', password='$password')"
        }
    }

//231efsd
}