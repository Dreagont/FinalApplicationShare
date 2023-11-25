package com.example.finalapplicationshare

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.database.FirebaseDatabase

class AddFolderActivity : AppCompatActivity() {
    private lateinit var folderNameEditText: EditText
    private lateinit var folderDescriptionEditText: EditText
    private lateinit var addFolderButton: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_folder)

        folderNameEditText  = findViewById(R.id.folderName)
        folderDescriptionEditText  = findViewById(R.id.folderDescription)
        addFolderButton = findViewById(R.id.btnAddFolder)

        addFolderButton.setOnClickListener {
            addFolderToDatabase()
        }
    }

    private fun addFolderToDatabase() {
        val folderName = folderNameEditText.text.toString().trim()
        val folderDescription = folderDescriptionEditText .text.toString().trim()

        if (folderName.isNotEmpty() && folderDescription.isNotEmpty()){
            val databaseReference = FirebaseDatabase.getInstance().getReference("Folders")
            val folderId = databaseReference.push().key

            val folder = Folder(folderId!!, folderName, folderDescription)

            databaseReference.child(folderId).setValue(folder)
                .addOnSuccessListener {
                    Toast.makeText(this, "Folder added successfully", Toast.LENGTH_SHORT).show()

                    val sharedPref  = getSharedPreferences("MySharedPref", MODE_PRIVATE)
                    sharedPref .edit().putBoolean("showFragmentFolder", true).apply()
                    finish()
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Failed to add folder", Toast.LENGTH_SHORT).show()
                }
        } else {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
        }
    }
}