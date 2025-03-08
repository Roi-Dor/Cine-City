package com.example.cinecity.network

import com.example.cinecity.models.TmdbMovie
import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.Request
import java.net.URLEncoder

object MovieApiClient {

    data class MovieSearchResponse(
        val page: Int,
        val results: List<TmdbMovie>,
        val total_pages: Int,
        val total_results: Int
    )



    private val client = OkHttpClient()

    // Replace the token below with your actual Bearer token from TMDB:
    private const val API_BEARER_TOKEN = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI3OGM3YmU3ZjhiMDkyYjE5YzgzY2EzNjQ1MDQ3MzY1NSIsIm5iZiI6MTc0MTE4NDM0NC4zOCwic3ViIjoiNjdjODVkNTg4MjFjMTliNWViZTcwMGYwIiwic2NvcGVzIjpbImFwaV9yZWFkIl0sInZlcnNpb24iOjF9.eOKBLSyYS7x2Hoxeixa8YiQOfT315t_B8CR4FP1jK4k"
    fun searchMovies(query: String): MovieSearchResponse? {
        val encodedQuery = URLEncoder.encode(query, "UTF-8")
        val request = Request.Builder()
            .url("https://api.themoviedb.org/3/search/multi?query=$encodedQuery&include_adult=false&language=en-US&page=1")
            .get()
            .addHeader("accept", "application/json")
            .addHeader("Authorization", API_BEARER_TOKEN)
            .build()

        client.newCall(request).execute().use { response ->
            if (!response.isSuccessful) return null
            val responseBody = response.body?.string() ?: return null

            // Parse the JSON
            return Gson().fromJson(responseBody, MovieSearchResponse::class.java)
        }
    }
}