package com.example.carpool2;



import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.FirebaseFirestore

class ChatRoomActivity : AppCompatActivity() {

private lateinit var messageEditText: EditText
private lateinit var sendButton: Button
private lateinit var chatRecyclerView: RecyclerView
private lateinit var chatAdapter: ChatAdapter
private lateinit var messageList: ArrayList<Message>
private lateinit var db: FirebaseFirestore
private lateinit var auth: FirebaseAuth

private var chatUser: User? = null
private var chatRoomId: String = ""

        override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_room)

        // Retrieve the chat user
        chatUser = intent.getSerializableExtra("chatUser") as User?

        // Initialize Firebase
        db = FirebaseFirestore.getInstance()
        auth = FirebaseAuth.getInstance()

        // Initialize UI elements
        messageEditText = findViewById(R.id.messageEditText)
        sendButton = findViewById(R.id.sendButton)
        chatRecyclerView = findViewById(R.id.chatRecyclerView)

        messageList = ArrayList()
        chatAdapter = ChatAdapter(messageList)
        chatRecyclerView.layoutManager = LinearLayoutManager(this)
        chatRecyclerView.adapter = chatAdapter

        chatUser?.let {
        chatRoomId = getChatRoomId(auth.currentUser!!.email!!, it.email)
        loadMessages(chatRoomId)
        }

        // Handle sending messages
        sendButton.setOnClickListener {
        val messageText = messageEditText.text.toString()
        if (messageText.isNotEmpty()) {
        sendMessage(messageText)
        }
        }
        }

private fun getChatRoomId(sender: String, receiver: String): String {
        return if (sender < receiver) "$receiver" else "$sender"
        }

private fun sendMessage(messageText: String) {
        val message = hashMapOf(
        "message" to messageText,
        "sender" to auth.currentUser?.email,
        "timestamp" to System.currentTimeMillis()
        )
        db.collection("chatRooms").document(chatRoomId).collection("messages")
        .add(message)
        .addOnSuccessListener {
        messageEditText.text.clear()
        }
        }

        override fun onBackPressed() {
                super.onBackPressed()
                // Navigate to HomeActivity when back button is pressed
                val intent = Intent(this, UserListActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
                finish() // Finish the current activity
        }

private fun loadMessages(chatRoomId: String) {
        db.collection("chatRooms").document(chatRoomId).collection("messages")
        .orderBy("timestamp")
        .addSnapshotListener { snapshots, _ ->
        for (dc in snapshots!!.documentChanges) {
        if (dc.type == DocumentChange.Type.ADDED) {
        val message = dc.document.toObject(Message::class.java)
        messageList.add(message)
        chatAdapter.notifyDataSetChanged()
        }
        }
        }
        }
        }
