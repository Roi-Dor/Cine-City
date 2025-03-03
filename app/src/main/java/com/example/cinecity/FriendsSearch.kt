package com.example.cinecity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.cinecity.databinding.ActivityFriendsSearchBinding

class FriendsSearch : AppCompatActivity() {

    private lateinit var binding: ActivityFriendsSearchBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFriendsSearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Handle click event for appLogo to return to MainActivity
        binding.appLogo.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish() // Optional: Close FriendsSearch activity
        }
        // Placeholder for future search logic
        // e.g., set up a listener for binding.searchEditText, etc.
    }
}