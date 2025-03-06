package com.example.cinecity.models

import java.time.LocalDate
import java.time.format.DateTimeFormatter

object DataManager {











    fun generateProgramList(): List<Program> {
        val programs = mutableListOf<Program>()
        programs.add(
            Program.Builder()
                .name("Wonka")
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
                .length(119)
                .poster("https://www.themoviedb.org/t/p/w600_and_h900_bestv2/uxBHXaoOvAwy4NpPpP7nNx2rXYQ.jpg")
                .overview("Third entry in the \"Deadpool\" franchise. Plot TBA.")
                .rating(0.0f)
                .releaseDate(LocalDate.parse("25/07/2024", DateTimeFormatter.ofPattern("dd/MM/yyyy")))
                .build()
        )
        programs.add(
            Program.Builder()
                .name("The Marvels")

                .length(105)
                .poster("https://www.themoviedb.org/t/p/w600_and_h900_bestv2/9GBhzXMFjgcZ3FdR9w3bUMMTps5.jpg")
                .overview("Carol Danvers, aka Captain Marvel, has reclaimed her identity from the tyrannical Kree and taken revenge on the Supreme Intelligence. But unintended consequences see Carol shouldering the burden of a destabilized universe. When her duties send her to an anomalous wormhole linked to a Kree revolutionary, her powers become entangled with that of Jersey City super-fan Kamala Khan, aka Ms. Marvel, and Carol’s estranged niece, now S.A.B.E.R. astronaut Captain Monica Rambeau. Together, this unlikely trio must team up and learn to work in concert to save the universe.")
                .rating(6.3f)
                .releaseDate(
                    LocalDate.parse(
                        "16/11/2023",
                        DateTimeFormatter.ofPattern("dd/MM/yyyy")
                    )
                ).build()
        )

        programs.add(
            Program.Builder()
                .name("Aquaman and the Lost Kingdom")
                .length(124)
                .poster("https://www.themoviedb.org/t/p/w600_and_h900_bestv2/7lTnXOy0iNtBAdRP3TZvaKJ77F6.jpg")
                .overview("Black Manta seeks revenge on Aquaman for his father's death. Wielding the Black Trident's power, he becomes a formidable foe. To defend Atlantis, Aquaman forges an alliance with his imprisoned brother. They must protect the kingdom.")
                .rating(6.9f)
                .releaseDate(
                    LocalDate.parse(
                        "21/12/2023",
                        DateTimeFormatter.ofPattern("dd/MM/yyyy")
                    )
                ).build()
        )

        programs.add(
            Program.Builder()
                .name("The Hunger Games: The Ballad of Songbirds & Snakes")
                .length(157)
                .poster("https://www.themoviedb.org/t/p/w600_and_h900_bestv2/mBaXZ95R2OxueZhvQbcEWy2DqyO.jpg")
                .overview("64 years before he becomes the tyrannical president of Panem, Coriolanus Snow sees a chance for a change in fortunes when he mentors Lucy Gray Baird, the female tribute from District 12.")
                .rating(7.2f)
                .releaseDate(
                    LocalDate.parse(
                        "07/12/2023",
                        DateTimeFormatter.ofPattern("dd/MM/yyyy")
                    )
                ).build()
        )
        return programs
    }
}
