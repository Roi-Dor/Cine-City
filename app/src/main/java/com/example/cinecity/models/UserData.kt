package com.example.cinecity.models
import java.time.LocalDate
import java.time.format.DateTimeFormatter

object UserData {
    fun generateProgramList(): List<Program> {
        val programs = mutableListOf<Program>()
        programs.add(
            Program.Builder()
                .name("Aquaman and the Lost Kingdom")
                .length(124)
                .poster("https://www.themoviedb.org/t/p/w600_and_h900_bestv2/7lTnXOy0iNtBAdRP3TZvaKJ77F6.jpg")
                .overview("Black Manta seeks revenge on Aquaman for his father's death. Wielding the Black Trident's power, he becomes a formidable foe. To defend Atlantis, Aquaman forges an alliance with his imprisoned brother. They must protect the kingdom.")
                .rating(6.9f)
                .build()
        )

        programs.add(
            Program.Builder()
                .name("The Hunger Games: The Ballad of Songbirds & Snakes")

                .length(157)
                .poster("https://www.themoviedb.org/t/p/w600_and_h900_bestv2/mBaXZ95R2OxueZhvQbcEWy2DqyO.jpg")
                .overview("64 years before he becomes the tyrannical president of Panem, Coriolanus Snow sees a chance for a change in fortunes when he mentors Lucy Gray Baird, the female tribute from District 12.")
                .rating(7.2f)
                .build()
        )
        return programs
    }
}