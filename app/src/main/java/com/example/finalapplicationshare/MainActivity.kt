package com.example.finalapplicationshare

import android.app.Dialog
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
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import android.view.Window
import android.view.WindowManager
//import androidx.databinding.DataBindingUtil
import com.example.finalapplicationshare.databinding.ActivityMainBinding
import org.checkerframework.common.subtyping.qual.Bottom


class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainerView, FragmentMain())
                .commit()
        }

        val addButton = findViewById<Button>(R.id.btnAdd)
        val btnProfile = findViewById<Button>(R.id.btnProfile)


        addButton.setOnClickListener{
            showCustomDialog()
        }

        btnProfile.setOnClickListener{
            val intent = Intent(this@MainActivity, ProfileActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun showCustomDialog() {
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.custom_dialog)

        dialog.window?.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT)
        dialog.window?.setWindowAnimations(R.style.DialogAnimation)
        dialog.window?.setGravity(Gravity.BOTTOM)
        dialog.show()

        val addFolder = dialog.findViewById<Button>(R.id.btnAddFolder)
        val addTopic = dialog.findViewById<Button>(R.id.btnAddTopic)

        addFolder.setOnClickListener {
            startActivity(Intent(this, AddFolderActivity::class.java))
            dialog.dismiss()
        }
        addTopic.setOnClickListener {
            startActivity(Intent(this, AddTopicActivity::class.java))
            dialog.dismiss()
        }
    }

    override fun onResume() {
        super.onResume()
        val sharedPref = getSharedPreferences("MySharedPref", MODE_PRIVATE)
        if (sharedPref.getBoolean("showFragmentFolder", false)) {
            sharedPref.edit().putBoolean("showFragmentFolder", false).apply()

            supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainerView, FragmentFolder())
                .commit()
            invalidateOptionsMenu()
        }
    }
}