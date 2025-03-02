package com.example.cinecity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cinecity.adapters.ProgramAdapter
import com.example.cinecity.databinding.ActivityMainBinding
import com.example.cinecity.models.DataManager

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val programAdapter = ProgramAdapter(DataManager.generateProgramList())
        binding.mainRVList.adapter = programAdapter
        binding.mainRVList.layoutManager = LinearLayoutManager(this)
    }
}
