package com.example.finalapplicationshare

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class ConfirmCode: AppCompatActivity() {
    private lateinit var btnConfirm: Button
    private lateinit var btnReceiveMail: Button
    private lateinit var etConfirmCode: EditText
    private lateinit var etUsername: EditText
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var databaseReference: DatabaseReference
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_confirm_code)

        btnConfirm = findViewById(R.id.btnConfirmCode)
        btnReceiveMail = findViewById(R.id.btnReceiveMail)
        etConfirmCode = findViewById(R.id.etConfirmCode)
        etUsername = findViewById(R.id.etUsername)

        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        databaseReference = FirebaseDatabase.getInstance().reference.child("users")
        auth = FirebaseAuth.getInstance()

        // vô hiệu hóa etConfirmCode và btnConfirm
        etConfirmCode.isEnabled = false
        btnConfirm.isEnabled = false

        btnReceiveMail.setOnClickListener {
            val username = etUsername.text.toString()

            // kiểm tra xem username có tồn tại
            databaseReference.child(username).addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        Toast.makeText(this@ConfirmCode, "Username exists. Enter confirmation code.", Toast.LENGTH_SHORT).show()
                        // Kích hoạt etConfirmCode và btnConfirm nếu username tồn tại
                        etConfirmCode.isEnabled = true
                        btnConfirm.isEnabled = true

                        val editor = sharedPreferences.edit()
                        editor.putString("username", username)
                        editor.apply()

                        btnReceiveMail.isEnabled = false
                        etUsername.isEnabled = false
                    } else {
                        Toast.makeText(this@ConfirmCode, "Username not found", Toast.LENGTH_SHORT).show()

                        etConfirmCode.isEnabled = false
                        btnConfirm.isEnabled = false
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(this@ConfirmCode, "Error: ${error.message}", Toast.LENGTH_SHORT).show()
                }
            })
        }

        btnConfirm.setOnClickListener {
            val enteredCode = etConfirmCode.text.toString()

            if (enteredCode == "123456") {

                val username = etUsername.text.toString()
                val editor = sharedPreferences.edit()
                editor.putString("username", username)
                editor.apply()

                val intent = Intent(this@ConfirmCode, ForgotPasswordActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this@ConfirmCode, "Incorrect confirmation code", Toast.LENGTH_SHORT).show()
            }
        }

    }
}