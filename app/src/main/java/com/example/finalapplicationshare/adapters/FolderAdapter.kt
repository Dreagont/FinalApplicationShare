package com.example.finalapplicationshare.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.finalapplicationshare.R // Replace with the actual package name
import com.example.finalapplicationshare.models.Folder

class FolderAdapter(private val context: Context, private val folderList: List<Folder>) :
    RecyclerView.Adapter<FolderAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.txtFolderName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.folder_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val folder = folderList[position]
        holder.titleTextView.text = folder.title
    }

    override fun getItemCount(): Int {
        return folderList.size
    }
}
