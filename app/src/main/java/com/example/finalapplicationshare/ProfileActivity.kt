package com.example.finalapplicationshare

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth

class ProfileActivity : AppCompatActivity() {

    private lateinit var tvEmail: TextView
    private lateinit var btnSave: Button
    private lateinit var ivAvatar: ImageView
    private lateinit var tvUsername: TextView

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        tvEmail = findViewById(R.id.tvEmail)
        btnSave = findViewById(R.id.btnEdit)
        ivAvatar = findViewById(R.id.ivAvatar)
        tvUsername = findViewById(R.id.tvUsername)


        val username = intent.getStringExtra("username")
        val email = intent.getStringExtra("email")
        val profileImage = intent.getStringExtra("profileImage")

        // Hiển thị thông tin trên giao diện
        tvUsername.text = username
        tvEmail.text = email

        Glide.with(this@ProfileActivity).load(profileImage).into(ivAvatar)


        btnSave.setOnClickListener {
        }
    }
}
