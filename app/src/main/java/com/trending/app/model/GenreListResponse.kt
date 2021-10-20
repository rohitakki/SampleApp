package com.trending.app.model

import com.google.gson.annotations.SerializedName

data class GenreListResponse(

	@field:SerializedName("genres")
	val genres: List<GenresItem?>? = null
)