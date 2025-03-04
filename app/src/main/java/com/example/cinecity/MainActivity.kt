package com.example.cinecity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cinecity.adapters.ProgramAdapter
import com.example.cinecity.databinding.ActivityMainBinding
import com.example.cinecity.fragments.BottomBarFragment
import com.example.cinecity.models.DataManager
import com.example.cinecity.utilities.ImageLoader

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var programAdapter: ProgramAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize your image loader
        ImageLoader.init(applicationContext)

        // Initialize and set the adapter
        programAdapter = ProgramAdapter(DataManager.generateProgramList())
        binding.mainRVList.adapter = programAdapter
        binding.mainRVList.layoutManager = LinearLayoutManager(this)

        // Add BottomBarFragment to the container
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_bottom_bar_container, BottomBarFragment())
            .commit()

        // Floating Action Button Click Listener
        binding.fabSearch.setOnClickListener {
            val intent = Intent(this, FriendsSearch::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        // When returning to MainActivity, refresh the data to ensure images are reloaded.
        programAdapter.updatePrograms(DataManager.generateProgramList())
        programAdapter.notifyDataSetChanged()
    }

    override fun onDestroy() {
        super.onDestroy()
        ImageLoader.getInstance().clear()
    }
}
