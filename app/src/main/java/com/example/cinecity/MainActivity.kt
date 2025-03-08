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

        ImageLoader.init(applicationContext)

        feedAdapter = FeedAdapter()
        binding.mainRVList.adapter = feedAdapter
        binding.mainRVList.layoutManager = LinearLayoutManager(this)

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_bottom_bar_container, BottomBarFragment())
            .commit()

        binding.fabSearch.setOnClickListener {
            startActivity(Intent(this, FriendsSearch::class.java))
        }
    }

    override fun onResume() {
        super.onResume()
        // Refresh the feed every time the activity resumes
        loadFeed()
    }

    // Loads the feed by retrieving the current user's friends' programs
    private fun loadFeed() {
        val currentUserId = AuthManager.getInstance(this).getCurrentUserUid()
        if (currentUserId == null) {
            Toast.makeText(this, "User not logged in", Toast.LENGTH_SHORT).show()
            return
        }

        FirebaseManager.getInstance().getUser(currentUserId, object : FirebaseManager.UserCallback {
            override fun onSuccess(currentUser: User) {
                val friendIds = currentUser.getFriendIds()
                if (friendIds.isEmpty()) {
                    // Update adapter with an empty feed if no friends exist
                    feedAdapter.updateFeedItems(emptyList())
                    return
                }
                val feedItems = mutableListOf<FeedItem>()
                var processedCount = 0

                // Iterate over each friend ID and load their programs asynchronously
                for (friendId in friendIds) {
                    FirebaseManager.getInstance().getUser(friendId, object : FirebaseManager.UserCallback {
                        override fun onSuccess(friendUser: User) {
                            // Convert the friend's programs Map to a List
                            val programsList = friendUser.programs?.values?.toList() ?: emptyList()
                            for (program in programsList) {
                                val feedItem = FeedItem(
                                    friendName = "${friendUser.firstName} ${friendUser.lastName}",
                                    friendProfileUrl = friendUser.profilePictureUrl ?: "",
                                    programPosterUrl = program.poster, // Ensure field names match your model
                                    programTitle = program.name,
                                    programRating = program.rating
                                )
                                feedItems.add(feedItem)
                            }
                            processedCount++
                            // Once all friend callbacks are processed, update the adapter
                            if (processedCount == friendIds.size) {
                                feedAdapter.updateFeedItems(feedItems)
                            }
                        }
                        override fun onFailure(errorMessage: String) {
                            // Even if a friend's data fails to load, count it as processed
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
