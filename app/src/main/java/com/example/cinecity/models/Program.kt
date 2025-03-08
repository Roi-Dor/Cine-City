package com.example.cinecity.models

data class Program(
    val poster: String = "",
    val name: String = "",
    val length: Int = 0,
    val overview: String = "",
    val release_date: String = "1-1-2000",
    val rating: Float = 0.0F,
    var isCollapsed: Boolean = true
) {
    // No-argument constructor required for Firebase
    constructor() : this("", "", 0, "", "1-1-2000",0.0F, true)

    fun toggleCollapse() = apply { this.isCollapsed = !this.isCollapsed }

    class Builder(
        private var poster: String = "",
        private var name: String = "",
        private var length: Int = 0,
        private var overview: String = "",
        private var release_date: String = "1-1-2000",
        private var rating: Float = 0.0F,
    ) {
        fun poster(poster: String) = apply { this.poster = poster }
        fun name(name: String) = apply { this.name = name }
        fun length(length: Int) = apply { this.length = length }
        fun overview(overview: String) = apply { this.overview = overview }
        fun rating(rating: Float) = apply { this.rating = rating }
        fun releaseDate(release_date: String) = apply {this.release_date = release_date}


        fun build() = Program(poster, name, length, overview, release_date , rating)
    }
}
