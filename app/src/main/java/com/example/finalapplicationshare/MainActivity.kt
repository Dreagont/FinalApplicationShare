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
import android.content.Context


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


    }

    public fun addWithAutoGenId() {
        val database = FirebaseDatabase.getInstance()
        val databaseReference = database.getReference("Users")

        val data = hashMapOf(
            "field1" to "value1",
            "field2" to "value2"
        )

        val newChildReference = databaseReference.push()
        newChildReference.setValue(data)
            .addOnSuccessListener {
                Toast.makeText(this,"Data added successfully with key: ${newChildReference.key}",Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener { e ->
                Toast.makeText(this,"Fail: ${newChildReference.key}",Toast.LENGTH_SHORT).show()
            }

    }
}