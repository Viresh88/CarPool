package com.example.carpool2


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth

class ChatAdapter(private val messageList: ArrayList<Message>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val MESSAGE_TYPE_SENT = 1
    private val MESSAGE_TYPE_RECEIVED = 2

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == MESSAGE_TYPE_SENT) {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_message_sent, parent, false)
            SentMessageViewHolder(view)
        } else {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_message_received, parent, false)
            ReceivedMessageViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val message = messageList[position]
        when (holder.itemViewType) {
            MESSAGE_TYPE_SENT -> (holder as SentMessageViewHolder).bind(message)
            MESSAGE_TYPE_RECEIVED -> (holder as ReceivedMessageViewHolder).bind(message)
        }
    }

    override fun getItemViewType(position: Int): Int {
        val message = messageList[position]
        return if (message.sender == FirebaseAuth.getInstance().currentUser?.email) {
            MESSAGE_TYPE_SENT
        } else {
            MESSAGE_TYPE_RECEIVED
        }
    }

    override fun getItemCount(): Int = messageList.size

    // ViewHolder for Sent Messages
    inner class SentMessageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val messageTextView: TextView = itemView.findViewById(R.id.messageTextView)

        fun bind(message: Message) {
            messageTextView.text = message.message
        }
    }

    // ViewHolder for Received Messages
    inner class ReceivedMessageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val messageTextView: TextView = itemView.findViewById(R.id.messageTextView)

        fun bind(message: Message) {
            messageTextView.text = message.message
        }
    }
}
