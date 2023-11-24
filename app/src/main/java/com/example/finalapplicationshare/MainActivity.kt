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
import org.checkerframework.common.subtyping.qual.Bottom


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val addButton = findViewById<Button>(R.id.btnAdd)

        addButton.setOnClickListener{
            showCustomDialog()
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
        val addTopic = dialog.findViewById<Button>(R.id.btnAddFolder)

        addFolder.setOnClickListener {
            startActivity(Intent(this, AddFolderActivity::class.java))
        }
        addTopic.setOnClickListener {
            startActivity(Intent(this, AddTopicActivity::class.java))
        }
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