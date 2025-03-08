package com.example.cinecity.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.cinecity.databinding.FeedItemBinding
import com.example.cinecity.models.FeedItem
import com.example.cinecity.utilities.ImageLoader

class FeedAdapter(private var feedItems: List<FeedItem> = emptyList()) :
    RecyclerView.Adapter<FeedAdapter.FeedViewHolder>() {

    // Method to update feed items and refresh the list.
    fun updateFeedItems(newFeedItems: List<FeedItem>) {
        feedItems = newFeedItems
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedViewHolder {
        val binding = FeedItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return FeedViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FeedViewHolder, position: Int) {
        val item = feedItems[position]
        holder.bind(item)


    }

    override fun getItemCount(): Int = feedItems.size

    class FeedViewHolder(private val binding: FeedItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: FeedItem) {
            // Display the program's title
            binding.programLBLTitle.text = item.programTitle
            // Set the rating given by the friend
            binding.programRBRating.rating = item.programRating
            // Load the program poster image
            ImageLoader.getInstance().loadImage(item.programPosterUrl, binding.programIMGPoster)
            // Load the friend’s profile picture
            ImageLoader.getInstance().loadImage(item.friendProfileUrl, binding.profileImage)
            // Optionally, set click listeners for further actions.
        }
    }
}
