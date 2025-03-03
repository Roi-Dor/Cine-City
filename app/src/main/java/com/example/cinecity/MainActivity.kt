package com.example.cinecity

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cinecity.adapters.ProgramAdapter
import com.example.cinecity.databinding.ActivityMainBinding
import com.example.cinecity.fragments.BottomBarFragment
import com.example.cinecity.models.DataManager
import com.example.cinecity.utilities.ImageLoader

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ImageLoader.init(this)

        val programAdapter = ProgramAdapter(DataManager.generateProgramList())
        binding.mainRVList.adapter = programAdapter
        binding.mainRVList.layoutManager = LinearLayoutManager(this)

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_bottom_bar_container, BottomBarFragment())
            .commit()

        // Floating Action Button Click Listener
        binding.fabSearch.setOnClickListener {
            val intent = Intent(this, FriendsSearch::class.java)
            startActivity(intent)
        }
    }
}
