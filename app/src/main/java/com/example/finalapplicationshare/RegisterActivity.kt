package com.example.finalapplicationshare

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class RegisterActivity : AppCompatActivity() {

    private lateinit var etUsername: EditText
    private lateinit var etPassword: EditText
    private lateinit var etEmail: EditText
    private lateinit var btnRegister: Button

    private lateinit var databaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        etUsername = findViewById(R.id.etUsername)
        etPassword = findViewById(R.id.etPass)
        etEmail = findViewById(R.id.etEmail)
        btnRegister = findViewById(R.id.btnRegister)

        databaseReference = FirebaseDatabase.getInstance().reference

        btnRegister.setOnClickListener { registerUser() }
    }

    private fun registerUser() {
        val username = etUsername.text.toString()
        val password = etPassword.text.toString()
        val email = etEmail.text.toString()

        if (username.isEmpty() || password.isEmpty() || email.isEmpty()) {
            showMessage("Please enter all information")
            return
        }

        if (password.length < 6) {
            showMessage("Password must be at least 6 characters")
            return
        }

        databaseReference.child("users").child(username).addListenerForSingleValueEvent(object :
            ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    showMessage("Username already exists. Please choose another username.")
                } else {
                    addValue(username, password, email, "Default.jpg")
                    val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                showMessage("Error: ${error.message}")
            }
        })
    }

    private fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun addValue(username: String, password: String, email: String, profileImage: String) {
        val user = User(username, password, email, "Default.jpg")
        databaseReference.child("users").child(username).setValue(user)    }
}

