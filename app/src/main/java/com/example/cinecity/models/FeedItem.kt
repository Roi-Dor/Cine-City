package com.example.cinecity.models

data class FeedItem(
    val friendName: String,         // e.g., "John Doe"
    val friendProfileUrl: String,   // Friend’s profile picture URL
    val programPosterUrl: String,   // Poster URL from one of the friend’s programs
    val programTitle: String,       // Title of that program
    val programRating: Float        // Rating given by the friend
)
