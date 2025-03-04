package com.example.cinecity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cinecity.adapters.ProgramAdapter
import com.example.cinecity.databinding.ActivityUserProfileBinding
import com.example.cinecity.models.Program
import com.example.cinecity.models.UserData

class UserProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUserProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Reuse the ProgramAdapter from MainActivity
        val programAdapter = ProgramAdapter(UserData.generateProgramList())
        binding.userRVList.adapter = programAdapter
        binding.userRVList.layoutManager = LinearLayoutManager(this)

        // Handle click event for appLogo to return to MainActivity
        binding.appLogo.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish() // Optional: Close FriendsSearch activity

        }
    }
}
