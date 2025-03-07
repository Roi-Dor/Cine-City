package com.example.cinecity.models

data class Program(
    val poster: String = "",
    val name: String = "",
    val length: Int = 0,
    val overview: String = "",
    val rating: Float = 0.0F,
    var isCollapsed: Boolean = true
) {
    // No-argument constructor required for Firebase
    constructor() : this("", "", 0, "", 0.0F, true)

    fun toggleCollapse() = apply { this.isCollapsed = !this.isCollapsed }

    class Builder(
        private var poster: String = "",
        private var name: String = "",
        private var length: Int = 0,
        private var overview: String = "",
        private var rating: Float = 0.0F,
    ) {
        fun poster(poster: String) = apply { this.poster = poster }
        fun name(name: String) = apply { this.name = name }
        fun length(length: Int) = apply { this.length = length }
        fun overview(overview: String) = apply { this.overview = overview }
        fun rating(rating: Float) = apply { this.rating = rating }

        fun build() = Program(poster, name, length, overview, rating)
    }
}
