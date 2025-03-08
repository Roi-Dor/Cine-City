package com.example.cinecity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cinecity.adapters.MyFriendsAdapter
import com.example.cinecity.databinding.ActivityMyFriendsBinding
import com.example.cinecity.models.Friend
import com.example.cinecity.models.User
import com.example.cinecity.utilities.AuthManager
import com.example.cinecity.utilities.FirebaseManager
import com.example.cinecity.utilities.ImageLoader

class MyFriendsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMyFriendsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyFriendsBinding.inflate(layoutInflater)
        setContentView(binding.root)


        // Set up RecyclerView with MyFriendsAdapter
        val myFriendsAdapter = MyFriendsAdapter(emptyList())
        binding.myFriendsRVList.adapter = myFriendsAdapter
        binding.myFriendsRVList.layoutManager = LinearLayoutManager(this)


        binding.appLogo.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish() // Optional: Close FriendsSearch activity
        }
        // Get current user ID from your authentication manager.
        val currentUserId = AuthManager.getInstance(this).getCurrentUserUid()
        if (currentUserId == null) {
            Toast.makeText(this, "User not logged in", Toast.LENGTH_SHORT).show()
            return
        }

        // Fetch current user's data.
        FirebaseManager.getInstance().getUser(currentUserId, object : FirebaseManager.UserCallback {
            override fun onSuccess(user: User) {
                // Convert friends map to a list of friend IDs using our helper.
                val friendIds = user.getFriendIds()
                if (friendIds.isEmpty()) {
                    myFriendsAdapter.updateList(emptyList())
                } else {
                    val friendsList = mutableListOf<Friend>()
                    var processedCount = 0
                    for (friendId in friendIds) {
                        FirebaseManager.getInstance().getUser(friendId, object : FirebaseManager.UserCallback {
                            override fun onSuccess(friendUser: User) {
                                val friend = Friend(
                                    profilePicUrl = friendUser.profilePictureUrl ?: "",
                                    userName = "${friendUser.firstName} ${friendUser.lastName}",
                                    uid = friendId
                                )
                                friendsList.add(friend)
                                processedCount++
                                if (processedCount == friendIds.size) {
                                    myFriendsAdapter.updateList(friendsList)
                                }
                            }

                            override fun onFailure(errorMessage: String) {
                                processedCount++
                                if (processedCount == friendIds.size) {
                                    myFriendsAdapter.updateList(friendsList)
                                }
                            }
                        })
                    }
                }
            }

            override fun onFailure(errorMessage: String) {
                Toast.makeText(this@MyFriendsActivity, errorMessage, Toast.LENGTH_SHORT).show()
            }
        })

    }
}