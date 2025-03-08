package com.example.cinecity.models

data class TmdbMovie(
    val poster_path: String?,
    val title: String?,
    val original_name: String?,
    val first_air_date: String?,
    val overview: String?,
    val release_date: String?,
    val vote_average: Double?,
    val media_type: String? // e.g. "movie", "tv", or "person"
)
