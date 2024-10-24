package com.example.carpool2.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.carpool2.ChatRoomActivity
import com.example.carpool2.entity.User
import com.example.carpool2.adapters.UserListAdapter
import com.example.carpool2.databinding.FragmentChatBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore


class ChatFragment : Fragment() {
 private lateinit var binding: FragmentChatBinding
    private lateinit var userListAdapter: UserListAdapter
    private lateinit var userList: ArrayList<User>
    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentChatBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize Firebase Auth and Firestore
        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        // Initialize UI Elements
        userList = ArrayList()
        userListAdapter = UserListAdapter(userList) { user ->
            val intent = Intent(requireContext(), ChatRoomActivity::class.java)
            intent.putExtra("chatUser", user)
            startActivity(intent)
        }

        // Set up RecyclerView
        binding.userRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.userRecyclerView.adapter = userListAdapter

        // Load users from Firestore or Dummy users
        //loadUsers()
        loadDummyUsers()
    }

    private fun loadDummyUsers() {
        // Create a list of dummy users
        userList.clear()
        userList.add(User("John Doe", "john.doe@example.com", "Online"))
        userList.add(User("Jane Smith", "jane.smith@example.com", "Offline"))
        userList.add(User("Alex Johnson", "alex.johnson@example.com", "Online"))
        userList.add(User("Emma Brown", "emma.brown@example.com", "Offline"))
        userList.add(User("Michael Green", "michael.green@example.com", "Online"))
        userList.add(User("Sophia Turner", "sophia.turner@example.com", "Offline"))
        userList.add(User("Chris Evans", "chris.evans@example.com", "Online"))

        // Notify adapter of data changes
        userListAdapter.notifyDataSetChanged()
    }

    private fun loadUsers() {
        val currentUser = auth.currentUser?.email
        db.collection("users")
            .get()
            .addOnSuccessListener { result ->
                userList.clear()
                for (document in result) {
                    val user = document.toObject(User::class.java)
                    if (user.email != currentUser) {
                        userList.add(user)
                    }
                }
                userListAdapter.notifyDataSetChanged()
            }
            .addOnFailureListener { exception ->
                Log.w("ChatFragment", "Error getting users: ", exception)
                Toast.makeText(requireContext(), "Failed to load users", Toast.LENGTH_SHORT).show()
            }
    }



}