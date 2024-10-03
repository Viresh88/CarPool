package com.example.carpool2



import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class UserListAdapter(
    private val userList: ArrayList<User>,
    private val clickListener: (User) -> Unit
) : RecyclerView.Adapter<UserListAdapter.UserViewHolder>() {

    class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val userName: TextView = itemView.findViewById(R.id.tv_user_name)
        val userStatus: TextView = itemView.findViewById(R.id.tv_user_status)
        val profileImage: ImageView = itemView.findViewById(R.id.iv_user_profile)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false)
        return UserViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = userList[position]
        holder.userName.text = user.name
        holder.userStatus.text = user.status
        holder.itemView.setOnClickListener { clickListener(user) }
    }

    override fun getItemCount(): Int = userList.size
}
