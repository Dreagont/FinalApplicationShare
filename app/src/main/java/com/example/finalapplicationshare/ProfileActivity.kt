package com.example.finalapplicationshare

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class ProfileActivity : AppCompatActivity() {

    private lateinit var tvEmail: TextView
    private lateinit var btnSave: Button
    private lateinit var ivAvatar: ImageView
    private lateinit var tvUsername: TextView

    private lateinit var auth: FirebaseAuth
    private lateinit var databaseReference: DatabaseReference
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        tvEmail = findViewById(R.id.tvEmail)
        btnSave = findViewById(R.id.btnEdit)
        ivAvatar = findViewById(R.id.ivAvatar)
        tvUsername = findViewById(R.id.tvUsername)

        auth = FirebaseAuth.getInstance()
        databaseReference = FirebaseDatabase.getInstance().reference.child("users")

        sharedPreferences = getSharedPreferences("userPrefs", Context.MODE_PRIVATE)

        val username = sharedPreferences.getString("username", "")

        if (!username.isNullOrEmpty()) {
            tvUsername.text = username

            databaseReference.child(username).addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        val user = snapshot.getValue(User::class.java)

                        if (user != null) {
                            // Cập nhật thông tin người dùng trên giao diện
                            tvUsername.text = user.username
                            tvEmail.text = user.email
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    // Xử lý lỗi nếu có
                }
            })

            // nhấn nút chỉnh sửa

        } else {

        }
    }
}
