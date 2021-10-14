package com.movie.app.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class DiscoverMoviesResponse(

	@field:SerializedName("page")
	val page: Int? = null,

	@field:SerializedName("results")
	val results: List<MovieItem?>? = null
): Serializable