package com.example.cinecity.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
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
            // If you have an ImageLoader or similar, load the profilePicUrl
            ImageLoader.getInstance().loadImage(friend.profilePicUrl, binding.profileImage)
        }
    }
}
