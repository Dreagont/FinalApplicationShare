package com.example.finalapplicationshare

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.finalapplicationshare.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class Profile : Fragment() {

    private lateinit var tvEmail: TextView
    private lateinit var btnSave: Button
    private lateinit var ivAvatar: ImageView
    private lateinit var tvUsername: TextView
    private lateinit var imBack: ImageView
    private lateinit var btnLogOut: Button

    private lateinit var auth: FirebaseAuth
    private lateinit var databaseReference: DatabaseReference
    private lateinit var sharedPreferences: SharedPreferences

    companion object {
        const val USER_PREFS = "userPrefs"
        const val USERNAME_KEY = "username"
        const val EMAIL_KEY = "email"
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        tvEmail = view.findViewById(R.id.tvEmail)
        btnSave = view.findViewById(R.id.btnEdit)
        ivAvatar = view.findViewById(R.id.ivAvatar)
        tvUsername = view.findViewById(R.id.tvUsername)
        imBack = view.findViewById(R.id.ivBack)
        btnLogOut = view.findViewById(R.id.btnLogOut)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        imBack.setOnClickListener{
            // Close the fragment or navigate back in the navigation stack
        }

        auth = FirebaseAuth.getInstance()
        databaseReference = FirebaseDatabase.getInstance().reference.child("users")

        sharedPreferences = requireActivity().getSharedPreferences(USER_PREFS, Context.MODE_PRIVATE)

        val username = sharedPreferences.getString(USERNAME_KEY, "")

        if (!username.isNullOrEmpty()) {
            tvUsername.text = username

            databaseReference.child(username).addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        val user = snapshot.getValue(User::class.java)

                        if (user != null) {
                            tvUsername.text = user.username
                            tvEmail.text = user.email
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    // Handle error if any
                }
            })

            btnLogOut.setOnClickListener {
                val editor = sharedPreferences.edit()
                editor.clear()
                editor.apply()

                auth.signOut()

                // Navigate to LoginActivity
                startActivity(Intent(requireContext(), LoginActivity::class.java))
                activity?.finish()
            }
        }
    }
}