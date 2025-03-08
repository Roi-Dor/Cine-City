package com.example.cinecity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cinecity.adapters.ProgramAdapter
import com.example.cinecity.databinding.ActivitySearchProgramsBinding
import com.example.cinecity.models.Program
import com.example.cinecity.network.MovieApiClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import com.example.cinecity.utilities.DateFormatter
import kotlin.math.log

class SearchProgramsActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySearchProgramsBinding
    private lateinit var programAdapter: ProgramAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchProgramsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Navigate back to main when logo is pressed
        binding.appLogo.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        binding.searchIcon.setOnClickListener {
            val searchQuery = binding.searchEditText.text.toString().trim()
            if (searchQuery.isNotEmpty()) {
                // Perform the search in the background
                performSearch(searchQuery)
            }
        }
    }

    private fun performSearch(searchQuery: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val response = MovieApiClient.searchMovies(searchQuery)

            withContext(Dispatchers.Main) {
                if (response == null) {
                    Toast.makeText(
                        this@SearchProgramsActivity,
                        "No results or error occurred.",
                        Toast.LENGTH_SHORT
                    ).show()
                    return@withContext
                }

                val programList = response.results
                    .map { tmdbMovie ->
                        val posterUrl = tmdbMovie.poster_path.orEmpty()
                        val overview = tmdbMovie.overview.orEmpty()
                        val voteAverage = tmdbMovie.vote_average ?: 0.0
                        val type = tmdbMovie.media_type.orEmpty()

                        // Determine title and raw release date based on media type.
                        val title: String
                        val releaseDateRaw: String
                        if (type == "movie") {
                            title = tmdbMovie.title.orEmpty()
                            releaseDateRaw = tmdbMovie.release_date ?: "2000-1-1"
                        } else {
                            title = tmdbMovie.name.orEmpty()
                            releaseDateRaw = tmdbMovie.first_air_date ?: "2000-1-1"
                        }

                        val releaseDate = DateFormatter.convertDate(releaseDateRaw)

                        Program.Builder()
                            .poster("https://image.tmdb.org/t/p/w500$posterUrl")
                            .name(title)
                            .length(0)
                            .overview(overview)
                            .rating(((tmdbMovie.vote_average ?: 0.0) / 2).toFloat()) // divide by 2 here only!
                            .releaseDate(releaseDate)
                            .build()
                    }

                setupRecyclerView(programList)
            }
        }
    }


    private fun setupRecyclerView(programs: List<Program>) {
        programAdapter = ProgramAdapter(programs.toMutableList())
        binding.movieSearchRVList.adapter = programAdapter
        binding.movieSearchRVList.layoutManager = LinearLayoutManager(this)
    }
}
