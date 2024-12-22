package com.example.cat.data.remote.dto

import com.google.gson.annotations.SerializedName

data class WeightDto(

	@field:SerializedName("metric")
	val metric: String? = null,

	@field:SerializedName("imperial")
	val imperial: String? = null
)