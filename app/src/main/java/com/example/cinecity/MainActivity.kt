package com.example.cinecity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cinecity.adapters.FeedAdapter
import com.example.cinecity.databinding.ActivityMainBinding
import com.example.cinecity.fragments.BottomBarFragment
import com.example.cinecity.models.FeedItem
import com.example.cinecity.models.User
import com.example.cinecity.utilities.AuthManager
import com.example.cinecity.utilities.FirebaseManager
import com.example.cinecity.utilities.ImageLoader

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var feedAdapter: FeedAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize ImageLoader early in your app lifecycle.
        ImageLoader.init(applicationContext)

        // Initialize and set up the feed adapter.
        feedAdapter = FeedAdapter()
        binding.mainRVList.adapter = feedAdapter
        binding.mainRVList.layoutManager = LinearLayoutManager(this)

        // Add BottomBarFragment to the container.
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_bottom_bar_container, BottomBarFragment())
            .commit()

        // Floating Action Button Click Listener.
        binding.fabSearch.setOnClickListener {
            val intent = Intent(this, FriendsSearch::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        // Load the feed from the current user's friends.
        loadFeed()
    }


    private fun loadFeed() {
        val currentUserId = AuthManager.getInstance(this).getCurrentUserUid()
        if (currentUserId == null) {
            Toast.makeText(this, "User not logged in", Toast.LENGTH_SHORT).show()
            return
        }

        FirebaseManager.getInstance().getUser(currentUserId, object : FirebaseManager.UserCallback {
            override fun onSuccess(currentUser: User) {
                // Get friend IDs from current user
                val friendIds = currentUser.getFriendIds()
                if (friendIds.isEmpty()) {
                    feedAdapter.updateFeedItems(emptyList())
                    return
                }
                val feedItems = mutableListOf<FeedItem>()
                var processedCount = 0

                // Iterate over each friend ID
                for (friendId in friendIds) {
                    FirebaseManager.getInstance().getUser(friendId, object : FirebaseManager.UserCallback {
                        override fun onSuccess(friendUser: User) {
                            // Convert the friend's programs Map to a List
                            val programsMap = friendUser.programs
                            val programsList = programsMap?.values?.toList() ?: emptyList()
                            // Create a FeedItem for every program of the friend
                            for (program in programsList) {
                                val feedItem = FeedItem(
                                    friendName = "${friendUser.firstName} ${friendUser.lastName}",
                                    friendProfileUrl = friendUser.profilePictureUrl ?: "",
                                    programPosterUrl = program.poster,  // Adjust field names per your Program model
                                    programTitle = program.name,
                                    programRating = program.rating
                                )
                                feedItems.add(feedItem)
                            }
                            processedCount++
                            if (processedCount == friendIds.size) {
                                feedAdapter.updateFeedItems(feedItems)
                            }
                        }
                        override fun onFailure(errorMessage: String) {
                            processedCount++
                            if (processedCount == friendIds.size) {
                                feedAdapter.updateFeedItems(feedItems)
                            }
                        }
                    })
                }
            }
            override fun onFailure(errorMessage: String) {
                Toast.makeText(this@MainActivity, errorMessage, Toast.LENGTH_SHORT).show()
            }
        })
    }


    override fun onDestroy() {
        super.onDestroy()
        ImageLoader.getInstance().clear()
    }
}
