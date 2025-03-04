package com.example.cinecity.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.cinecity.FriendProfileActivity
import com.example.cinecity.models.Friend
import com.example.cinecity.databinding.ProfileItemBinding
import com.example.cinecity.utilities.ImageLoader

class MyFriendsAdapter(private val friendsList: List<Friend>) :
    RecyclerView.Adapter<MyFriendsAdapter.MyFriendsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyFriendsViewHolder {
        val binding = ProfileItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return MyFriendsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyFriendsViewHolder, position: Int) {
        val friend = friendsList[position]
        holder.bind(friend)
    }

    override fun getItemCount(): Int = friendsList.size

    class MyFriendsViewHolder(private val binding: ProfileItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(friend: Friend) {
            binding.username.text = friend.userName
            // Load the profile picture using your ImageLoader:
            ImageLoader.getInstance().loadImage(friend.profilePicUrl, binding.profileImage)

            // Set the click listener on the profile image
            binding.profileImage.setOnClickListener {
                val context = binding.root.context
                val intent = Intent(context, FriendProfileActivity::class.java)
                intent.putExtra("FRIEND_USERNAME", friend.userName)
                context.startActivity(intent)
            }
        }
    }
}
