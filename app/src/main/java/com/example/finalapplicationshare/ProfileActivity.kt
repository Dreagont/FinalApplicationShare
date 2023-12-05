package com.example.finalapplicationshare

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.finalapplicationshare.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class ProfileActivity : AppCompatActivity() {

    private lateinit var tvEmail: TextView
    private lateinit var btnSave: Button
    private lateinit var ivAvatar: ImageView
    private lateinit var tvUsername: TextView
    private lateinit var imBack: ImageView
    private lateinit var btnLogOut: Button

    private lateinit var auth: FirebaseAuth
    private lateinit var databaseReference: DatabaseReference
    private lateinit var sharedPreferences: SharedPreferences

    companion object {
        const val USER_PREFS = "userPrefs"
        const val USERNAME_KEY = "username"
        const val EMAIL_KEY = "email"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        tvEmail = findViewById(R.id.tvEmail)
        btnSave = findViewById(R.id.btnEdit)
        ivAvatar = findViewById(R.id.ivAvatar)
        tvUsername = findViewById(R.id.tvUsername)
        imBack = findViewById(R.id.ivBack)
        btnLogOut = findViewById(R.id.btnLogOut)

        imBack.setOnClickListener{
            finish()
        }

        auth = FirebaseAuth.getInstance()
        databaseReference = FirebaseDatabase.getInstance().reference.child("users")

        sharedPreferences = getSharedPreferences(USER_PREFS, Context.MODE_PRIVATE)

        val username = sharedPreferences.getString(USERNAME_KEY, "")

        if (!username.isNullOrEmpty()) {
            tvUsername.text = username

            databaseReference.child(username).addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        val user = snapshot.getValue(User::class.java)

                        if (user != null) {
                            tvUsername.text = user.username
                            tvEmail.text = user.email
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    // Handle error if any
                }
            })

            btnLogOut.setOnClickListener {
                val editor = sharedPreferences.edit()
                editor.clear()
                editor.apply()

                auth.signOut()

                startActivity(Intent(this@ProfileActivity, LoginActivity::class.java))
                finish()
            }

        } else {
        }
    }
}
