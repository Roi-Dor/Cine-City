package com.example.cinecity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cinecity.adapters.ProgramAdapter
import com.example.cinecity.databinding.ActivityFriendProfileBinding
import com.example.cinecity.models.GalData
import com.example.cinecity.models.Program
import com.example.cinecity.models.UserData

class FriendProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFriendProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFriendProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Retrieve friend username from the Intent extras.
        val friendUsername = intent.getStringExtra("FRIEND_USERNAME") ?: "Friend's Library"
        binding.friendUsernameTitle.text = friendUsername

        // Load friend's programs (replace with your actual friend program data retrieval).
        //val friendProgramList: List<Program> = getFriendPrograms()

        val programAdapter = ProgramAdapter((GalData.generateProgramList()))//just for testing delete this once there is a database
        binding.friendRVList.adapter = programAdapter
        binding.friendRVList.layoutManager = LinearLayoutManager(this)

        // Handle click on appLogo to return to MainActivity (or any other navigation).
        binding.appLogo.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    // Dummy method to generate friend-specific program data.
    private fun getFriendPrograms(): List<Program> {
        // TODO: Replace with real friend data (e.g., query a database or API).

        return listOf() // Or generate a list similar to UserData.generateProgramList()
    }
}
