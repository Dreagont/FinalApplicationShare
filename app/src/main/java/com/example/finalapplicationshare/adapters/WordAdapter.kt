package com.example.finalapplicationshare.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.finalapplicationshare.R
import com.example.finalapplicationshare.models.Word

class WordAdapter(private val context: Context, private val wordList: List<Word>) :
    RecyclerView.Adapter<WordAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val wordTextView: TextView = itemView.findViewById(R.id.txtWordName)
        val translationTextView: TextView = itemView.findViewById(R.id.txtWordMean)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.words_display_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val word = wordList[position]
        holder.wordTextView.text = word.term
        holder.translationTextView.text = word.definition
    }

    override fun getItemCount(): Int {
        return wordList.size
    }
}
