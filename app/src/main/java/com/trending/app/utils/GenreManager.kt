package com.trending.app.utils

import com.trending.app.model.GenreListResponse
import com.trending.app.model.GenresItem

class GenreManager {
    private var genreMap: HashMap<Int?, GenresItem?> = HashMap()

    companion object {
        var mInstance: GenreManager? = GenreManager()
    }

    fun addGenres(genreListResponse: GenreListResponse?) {
        if (genreListResponse != null && !genreListResponse.genres.isNullOrEmpty()) {
            for (index in genreListResponse.genres.indices) {
                genreMap[genreListResponse.genres[index]?.id] = genreListResponse.genres[index]
            }
        }
    }

    fun getGenresForMovie(genreIds: List<Int>?): String {
        var genreString = ""
        for (index in genreIds?.indices!!) {
            if (genreMap.containsKey(genreIds[index])) {
                genreString += genreMap[genreIds[index]]?.name
            }
            if (index != genreIds.size-1) {
                genreString += ", "
            }
        }

        return genreString
    }
}