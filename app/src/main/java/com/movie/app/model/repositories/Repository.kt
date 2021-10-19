package com.movie.app.model.repositories

import com.google.gson.annotations.SerializedName

data class Repository(

	@field:SerializedName("forks")
	val forks: Int? = null,

	@field:SerializedName("starsSince")
	val starsSince: Int? = null,

	@field:SerializedName("builtBy")
	val builtBy: List<Developer?>? = null,

	@field:SerializedName("totalStars")
	val totalStars: Int? = null,

	@field:SerializedName("rank")
	val rank: Int? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("language")
	val language: String? = null,

	@field:SerializedName("languageColor")
	val languageColor: String? = null,

	@field:SerializedName("repositoryName")
	val repositoryName: String? = null,

	@field:SerializedName("url")
	val url: String? = null,

	@field:SerializedName("username")
	val username: String? = null,

	@field:SerializedName("since")
	val since: String? = null
)