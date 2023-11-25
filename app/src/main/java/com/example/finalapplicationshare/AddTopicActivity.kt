package com.example.finalapplicationshare

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.Toast
import com.google.firebase.database.FirebaseDatabase

class AddTopicActivity : AppCompatActivity() {
    private lateinit var topicNameEditText: EditText
    private lateinit var topicDescriptionEditText: EditText
    private lateinit var visibilityRadioGroup: RadioGroup
    private lateinit var addTopicButton: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_topic)

        topicNameEditText = findViewById(R.id.topicName)
        topicDescriptionEditText = findViewById(R.id.topicDescription)
        visibilityRadioGroup = findViewById(R.id.chooseVisible)
        addTopicButton = findViewById(R.id.btnAddTopic)

        addTopicButton.setOnClickListener { addTopicToDataBase() }
    }

    private fun addTopicToDataBase() {
        val topicTitle = topicNameEditText.text.toString().trim()
        val visibility = if (visibilityRadioGroup.checkedRadioButtonId == R.id.radio_private) "private" else "public"

        if (topicTitle.isNotEmpty()) {
            val databaseReference = FirebaseDatabase.getInstance().getReference("Topics")
            val topicId = databaseReference.push().key ?: return

            val topic = Topic(topicId, topicTitle, visibility)

            databaseReference.child(topicId).setValue(topic)
                .addOnSuccessListener {
                    Toast.makeText(this, "Topic added successfully", Toast.LENGTH_SHORT).show()
                    finish()
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Failed to add topic", Toast.LENGTH_SHORT).show()
                }
        } else {
            Toast.makeText(this, "Please enter the topic title", Toast.LENGTH_SHORT).show()
        }
    }
}