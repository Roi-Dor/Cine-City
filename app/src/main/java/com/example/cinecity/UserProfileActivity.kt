package com.example.cinecity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cinecity.adapters.ProgramAdapter
import com.example.cinecity.databinding.ActivityUserProfileBinding
import com.example.cinecity.models.Program
import com.example.cinecity.utilities.FirebaseManager
import com.google.firebase.auth.FirebaseAuth

class UserProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUserProfileBinding
    private lateinit var programAdapter: ProgramAdapter
    private val programList = mutableListOf<Program>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 1) Set up RecyclerView and adapter
        programAdapter = ProgramAdapter(programList)
        binding.userRVList.layoutManager = LinearLayoutManager(this)
        binding.userRVList.adapter = programAdapter

        // 2) Fetch user programs from Firebase
        val currentUser = FirebaseAuth.getInstance().currentUser
        if (currentUser != null) {
            FirebaseManager.getInstance().getUserPrograms(
                currentUser.uid,
                object : FirebaseManager.ProgramsCallback {
                    override fun onSuccess(programs: List<Program>) {
                        // Clear old data and add new
                        programList.clear()
                        programList.addAll(programs)
                        // Tell the adapter we have new data
                        programAdapter.notifyDataSetChanged()

                        Toast.makeText(
                            this@UserProfileActivity,
                            "Programs loaded successfully!",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    override fun onFailure(errorMessage: String) {
                        Toast.makeText(
                            this@UserProfileActivity,
                            "Error loading programs: $errorMessage",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            )
        } else {
            Toast.makeText(this, "User not logged in", Toast.LENGTH_SHORT).show()
        }

        // Handle click event for appLogo to return to MainActivity
        binding.appLogo.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}

