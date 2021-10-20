package com.trending.app.model.repositories

import com.google.gson.annotations.SerializedName

data class Developer(

	@field:SerializedName("avatar")
	val avatar: String? = null,

	@field:SerializedName("url")
	val url: String? = null,

	@field:SerializedName("username")
	val username: String? = null
)