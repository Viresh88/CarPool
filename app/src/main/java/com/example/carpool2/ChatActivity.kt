package com.example.carpool2;



import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore

class UserListActivity : AppCompatActivity() {

    private lateinit var userRecyclerView: RecyclerView
    private lateinit var userListAdapter: UserListAdapter
    private lateinit var userList: ArrayList<User>
    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        // Initialize Firebase Auth and Firestore
        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()


        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomNavigationView.selectedItemId = R.id.profile

        bottomNavigationView.itemIconTintList = ContextCompat.getColorStateList(this, R.color.primary_dark)
        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> {
                    val intent = Intent(this, HomePageActivity::class.java)
                    startActivity(intent)
                    finish()
                    true
                }

                R.id.myRides -> {
                    val intent = Intent(this, MyRidesActivity::class.java)
                    startActivity(intent)
                    finish()
                    true
                }

                R.id.chat -> {
                    val intent = Intent(this, UserListActivity::class.java)
                    startActivity(intent)
                    finish()
                    true
                }

                R.id.requestRide -> {
                    val intent = Intent(this, RequestRideActivity::class.java)
                    startActivity(intent)
                    finish()
                    true
                }

                R.id.profile -> {
                    val intent = Intent(this, ProfileActivity::class.java)
                    startActivity(intent)
                    finish()
                    true
                }

                else -> false
            }
        }

        // Initialize UI Elements
        userRecyclerView = findViewById(R.id.userRecyclerView)
        userRecyclerView.layoutManager = LinearLayoutManager(this)
        userList = ArrayList()
        userListAdapter = UserListAdapter(userList) { user ->
            val intent = Intent(this, ChatRoomActivity::class.java)
            intent.putExtra("chatUser", user)
            startActivity(intent)
        }
        userRecyclerView.adapter = userListAdapter

        // Load users from Firestore
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

    override fun onBackPressed() {
        super.onBackPressed()
        // Navigate to HomeActivity when back button is pressed
        val intent = Intent(this, HomePageActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
        finish() // Finish the current activity
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
                Log.w("UserListActivity", "Error getting users: ", exception)
                Toast.makeText(this, "Failed to load users", Toast.LENGTH_SHORT).show()
            }
    }
}
