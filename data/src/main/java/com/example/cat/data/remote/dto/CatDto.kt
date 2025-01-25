package com.example.cat.data.remote.dto

import com.google.gson.annotations.SerializedName

data class CatDto(

	@field:SerializedName("width")
	val width: Int? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("url")
	val url: String? = null,

	@field:SerializedName("breeds")
	val breeds: List<BreedsItemDto>? = null,

	@field:SerializedName("height")
	val height: Int? = null
)