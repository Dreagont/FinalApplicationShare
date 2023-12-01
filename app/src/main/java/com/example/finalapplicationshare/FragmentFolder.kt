package com.example.finalapplicationshare

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.finalapplicationshare.adapters.FolderAdapter
import com.example.finalapplicationshare.models.Folder

class FragmentFolder : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_folder, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.folder_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.back -> {
                Toast.makeText(activity, "Backing", Toast.LENGTH_SHORT).show()
                true
            }
            R.id.addTopic -> {
                Toast.makeText(activity, "Add Topic", Toast.LENGTH_SHORT).show()
                true
            }
            R.id.settings -> {
                Toast.makeText(activity, "Setting", Toast.LENGTH_SHORT).show()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val folderList = createFolderList()

        val recyclerView: RecyclerView = view.findViewById(R.id.folderDisplayView)

        val layoutManager = LinearLayoutManager(activity)
        recyclerView.layoutManager = layoutManager

        val adapter = FolderAdapter(requireContext(), folderList)
        recyclerView.adapter = adapter
    }

    private fun createFolderList(): List<Folder> {
        // Create a list of Folder objects
        return listOf(
            Folder("1", "Folder 1", "Description 1"),
            Folder("2", "Folder 2", "Description 2"),
            Folder("3", "Folder 3", "Description 3"),
            // Add more folders as needed
        )
    }
}