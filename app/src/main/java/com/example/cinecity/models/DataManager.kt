package com.example.cinecity.models

import java.time.LocalDate
import java.time.format.DateTimeFormatter

object DataManager {
    fun generateProgramList(): List<Program> {
        val programs = mutableListOf<Program>()
        programs.add(
            Program.Builder()
                .name("Wonka")
                .genre(listOf("Comedy", "Family", "Fantasy"))
                .actors(listOf("Timothée Chalamet", "Calah Lane", "Keegan-Michael Key", "Hugh Grant"))
                .length(117)
                .poster("https://www.themoviedb.org/t/p/w600_and_h900_bestv2/qhb1qOilapbapxWQn9jtRCMwXJF.jpg")
                .overview("Willy Wonka - chock-full of ideas and determined to change the world one delectable bite at a time - is proof that the best things in life begin with a dream, and if you're lucky enough to meet Willy Wonka, anything is possible.")
                .rating(7.2f)
                .releaseDate(LocalDate.parse("13/12/2023", DateTimeFormatter.ofPattern("dd/MM/yyyy")))
                .build()
        )

        programs.add(
            Program.Builder()
                .name("Deadpool & Wolverine")
                .genre(listOf("Action", "Comedy", "Science Fiction"))
                .actors(listOf("Ryan Reynolds", "Hugh Jackman", "Morena Baccarin", "Emma Corrin"))
                .length(119)
                .poster("https://www.themoviedb.org/t/p/w600_and_h900_bestv2/uxBHXaoOvAwy4NpPpP7nNx2rXYQ.jpg")
                .overview("Third entry in the \"Deadpool\" franchise. Plot TBA.")
                .rating(0.0f)
                .releaseDate(LocalDate.parse("25/07/2024", DateTimeFormatter.ofPattern("dd/MM/yyyy")))
                .build()
        )

        // Continue for other entries...

        return programs
    }
}
