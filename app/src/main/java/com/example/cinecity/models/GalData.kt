package com.example.cinecity.models
import java.time.LocalDate
import java.time.format.DateTimeFormatter

object GalData {
    fun generateProgramList(): List<Program> {
        val programs = mutableListOf<Program>()
        programs.add(
            Program.Builder()
                .name("Barbie")
                .genre(ArrayList(mutableListOf("Comedy", "Adventure")))
                .actors(
                    ArrayList(
                        mutableListOf(
                            "Margot Robbie",
                            "Ryan Gosling",
                            "America Ferrera",
                            "Kate McKinnon"
                        )
                    )
                )
                .length(114)
                .poster("https://www.themoviedb.org/t/p/w600_and_h900_bestv2/iuFNMS8U5cb6xfzi51Dbkovj7vM.jpg")
                .overview("Barbie and Ken are having the time of their lives in the colorful and seemingly perfect world of Barbie Land. However, when they get a chance to go to the real world, they soon discover the joys and perils of living among humans.")
                .rating(7.1f)
                .releaseDate(
                    LocalDate.parse(
                        "20/07/2023",
                        DateTimeFormatter.ofPattern("dd/MM/yyyy")
                    )
                ).build()
        )

        programs.add(
            Program.Builder()
                .name("Argylle")
                .genre(ArrayList(mutableListOf("Comedy", "Action", "Crime")))
                .actors(
                    ArrayList(
                        mutableListOf(
                            "Bryce Dallas Howard",
                            "Henry Cavill",
                            "Bryan Cranston",
                            "Catherine O'Hara"
                        )
                    )
                )
                .length(139)
                .poster("https://www.themoviedb.org/t/p/w600_and_h900_bestv2/siduVKgOnABO4WH4lOwPQwaGwJp.jpg")
                .overview("When the plots of reclusive author Elly Conway's fictional espionage novels begin to mirror the covert actions of a real-life spy organization, quiet evenings at home become a thing of the past. Accompanied by her cat Alfie and Aiden, a cat-allergic spy, Elly races across the world to stay one step ahead of the killers as the line between Conway's fictional world and her real one begins to blur.")
                .rating(6.2f)
                .releaseDate(
                    LocalDate.parse(
                        "01/02/2023",
                        DateTimeFormatter.ofPattern("dd/MM/yyyy")
                    )
                ).build()
        )

        programs.add(
            Program.Builder()
                .name("Gran Turismo")
                .genre(ArrayList(mutableListOf("Adventure", "Action", "Drama")))
                .actors(
                    ArrayList(
                        mutableListOf(
                            "Archie Madekwe",
                            "David Harbour",
                            "Orlando Bloom",
                            "Darren Barnet"
                        )
                    )
                )
                .length(135)
                .poster("https://www.themoviedb.org/t/p/w600_and_h900_bestv2/51tqzRtKMMZEYUpSYkrUE7v9ehm.jpg")
                .overview("The ultimate wish-fulfillment tale of a teenage Gran Turismo player whose gaming skills won him a series of Nissan competitions to become an actual professional racecar driver.")
                .rating(7.9f)
                .releaseDate(
                    LocalDate.parse(
                        "09/08/2023",
                        DateTimeFormatter.ofPattern("dd/MM/yyyy")
                    )
                ).build()
        )
        return programs
    }
}