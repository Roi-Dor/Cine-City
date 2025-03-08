package com.example.cinecity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cinecity.adapters.ProgramAdapter
import com.example.cinecity.databinding.ActivityFriendProfileBinding
import com.example.cinecity.models.GalData
import com.example.cinecity.models.Program
import com.example.cinecity.models.UserData
import com.example.cinecity.utilities.AuthManager
import com.example.cinecity.utilities.FirebaseManager

class FriendProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFriendProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFriendProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Retrieve friend username from the Intent extras.
        val friendUsername = intent.getStringExtra("FRIEND_USERNAME") ?: "Friend's Library"
        binding.friendUsernameTitle.text = friendUsername


        val programAdapter = ProgramAdapter((GalData.generateProgramList()))//just for testing delete this once there is a database
        binding.friendRVList.adapter = programAdapter
        binding.friendRVList.layoutManager = LinearLayoutManager(this)

        // Handle click on appLogo to return to MainActivity (or any other navigation).
        binding.appLogo.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        // Retrieve the friend's ID from Intent extras
        val friendId = intent.getStringExtra("FRIEND_ID")
// Also, retrieve the friend username as you already do
        binding.friendUsernameTitle.text = friendUsername

// Set click listener for the add icon
        binding.addIcon.setOnClickListener {
            if (friendId != null) {
                // Get the current user's ID (this depends on how you manage authentication)
                // For example, if you have an AuthManager:
                val currentUserId = AuthManager.getInstance(this).getCurrentUserUid()
                if (currentUserId != null) {
                    FirebaseManager.getInstance().addFriend(currentUserId, friendId, object : FirebaseManager.FirebaseCallback {
                        override fun onSuccess() {
                            Toast.makeText(this@FriendProfileActivity, "Friend added!", Toast.LENGTH_SHORT).show()
                            val intent = Intent(this@FriendProfileActivity, MainActivity::class.java)
                            startActivity(intent)
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
