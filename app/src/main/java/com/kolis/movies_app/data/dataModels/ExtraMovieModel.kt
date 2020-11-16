package com.kolis.movies_app.data.dataModels


data class ExtraMovieModel(
    val release_date: String,
    val genres: List<GenreModel>,
    val casts: List<CastModel>,
    val crew: List<CrewModel>
) {

    fun getPrettyCasts(): String {
        val numOfCasts = casts.size
        return if (numOfCasts > 0) {
            var text = ""
            casts.forEach { text += it.name + " (" + it.character + "), " }
            text.substring(0, text.length - 2)
        } else {
            ""
        }
    }

    fun getPrettyDirectors(): String {
        val directors = crew.filter { it.isDirector() }
        val numOfDirectors = directors.size

        return if (numOfDirectors > 0) {
            var text = ""
            directors.forEach { text += it.name + ", " }
            text.substring(0, text.length - 2)
        } else {
            ""
        }

    }

    fun getPrettyGenres(): String {
        val numOfGeners = genres.size
        return if (numOfGeners > 0) {
            var geners = ""
            genres.forEach { geners += it.name + ", " }
            geners.substring(0, geners.length - 2)
        } else {
            ""
        }
    }
}
