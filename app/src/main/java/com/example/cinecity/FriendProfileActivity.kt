package com.example.cinecity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cinecity.adapters.ProgramAdapter
import com.example.cinecity.databinding.ActivityFriendProfileBinding
import com.example.cinecity.models.Program
import com.example.cinecity.utilities.AuthManager
import com.example.cinecity.utilities.FirebaseManager

class FriendProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFriendProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFriendProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Retrieve friend's username and ID from Intent extras.
        val friendUsername = intent.getStringExtra("FRIEND_USERNAME") ?: "Friend's Library"
        val friendId = intent.getStringExtra("FRIEND_ID")
        binding.friendUsernameTitle.text = friendUsername

        // Set up ProgramAdapter with an initially empty list.
        val programAdapter = ProgramAdapter(emptyList())
        binding.friendRVList.adapter = programAdapter
        binding.friendRVList.layoutManager = LinearLayoutManager(this)

        // Retrieve and display the friend's programs from Firebase.
        if (friendId != null) {
            FirebaseManager.getInstance().getUserPrograms(friendId, object : FirebaseManager.ProgramsCallback {
                override fun onSuccess(programs: List<Program>) {
                    // Update adapter with fetched programs.
                    programAdapter.updatePrograms(programs)
                }
                override fun onFailure(errorMessage: String) {
                    Toast.makeText(this@FriendProfileActivity, "Error loading programs: $errorMessage", Toast.LENGTH_SHORT).show()
                }
            })
        } else {
            Toast.makeText(this, "Friend ID not available", Toast.LENGTH_SHORT).show()
        }

        // Handle click on appLogo to return to MainActivity.
        binding.appLogo.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        // Set click listener for the add icon to add the friend.
        binding.addIcon.setOnClickListener {
            if (friendId != null) {
                // Get the current user's ID from your AuthManager.
                val currentUserId = AuthManager.getInstance(this).getCurrentUserUid()
                if (currentUserId != null) {
                    FirebaseManager.getInstance().addFriend(currentUserId, friendId, object : FirebaseManager.FirebaseCallback {
                        override fun onSuccess() {
                            Toast.makeText(this@FriendProfileActivity, "Friend added!", Toast.LENGTH_SHORT).show()
                            startActivity(Intent(this@FriendProfileActivity, MainActivity::class.java))
                            finish()
                        }
                        override fun onFailure(errorMessage: String) {
                            Toast.makeText(this@FriendProfileActivity, "Error: $errorMessage", Toast.LENGTH_SHORT).show()
                        }
                    })
                } else {
                    Toast.makeText(this@FriendProfileActivity, "User not logged in", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this@FriendProfileActivity, "Friend ID not available", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
