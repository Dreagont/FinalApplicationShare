package com.example.finalapplicationshare

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.finalapplicationshare.models.Topic
import com.example.finalapplicationshare.models.Word // Make sure this is the correct import for your Word class
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.FirebaseDatabase

class AddTopicActivity : AppCompatActivity() {
    private lateinit var topicNameEditText: EditText
    private lateinit var topicDescriptionEditText: EditText
    private lateinit var visibilityRadioGroup: RadioGroup
    private lateinit var addTopicFab: FloatingActionButton
    private lateinit var topicContainer: LinearLayout
    private lateinit var doneButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_topic)

        topicNameEditText = findViewById(R.id.topicName)
        topicDescriptionEditText = findViewById(R.id.topicDescription)
        visibilityRadioGroup = findViewById(R.id.chooseVisible)
        addTopicFab = findViewById(R.id.btnAddTopic)
        topicContainer = findViewById(R.id.container)
        doneButton = findViewById(R.id.btnDone)

        addTopicBox()
        addTopicBox()

        addTopicFab.setOnClickListener {
            addTopicBox()
        }

        doneButton.setOnClickListener {
            addTopicToDataBase()
        }
    }

    private fun addTopicToDataBase() {
        val topicTitle = topicNameEditText.text.toString().trim()
        val topicDescription = topicDescriptionEditText.text.toString().trim()
        val visibility = if (visibilityRadioGroup.checkedRadioButtonId == R.id.radio_private) "private" else "public"


        val wordsMap = mutableMapOf<String, Word>()


        for (i in 0 until topicContainer.childCount) {
            val boxView = topicContainer.getChildAt(i) as? LinearLayout
            val termEditText = boxView?.findViewById<EditText>(R.id.termEditText)
            val definitionEditText = boxView?.findViewById<EditText>(R.id.definitionEditText)

            val term = termEditText?.text.toString().trim()
            val definition = definitionEditText?.text.toString().trim()

            if (term.isNotEmpty() && definition.isNotEmpty()) {
                wordsMap[term] = Word(term, definition)
            }
        }

        if (wordsMap.size >= 2) {
            val topicId = FirebaseDatabase.getInstance().getReference("Topics").push().key ?: return
            val newTopic = Topic(topicId, topicTitle, visibility, topicDescription, "No", wordsMap)

            saveTopic(newTopic)
        } else {
            Toast.makeText(this, "Please fill out at least two topics", Toast.LENGTH_SHORT).show()
        }
    }

    private fun saveTopic(topic: Topic) {
        val databaseReference = FirebaseDatabase.getInstance().getReference("Topics")
        databaseReference.child(topic.id).setValue(topic)
            .addOnSuccessListener {
                Toast.makeText(this, "Topic added successfully", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                Toast.makeText(this, "Failed to add topic", Toast.LENGTH_SHORT).show()
            }
    }

    private fun addTopicBox() {
        val boxView = LayoutInflater.from(this).inflate(R.layout.topic_box, topicContainer, false)
        topicContainer.addView(boxView)
    }
}
