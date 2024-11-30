package com.example.worlddata

import androidx.annotation.DrawableRes


/**
 * The country item data class. Holds the parameter on which the rating is done.
 */
data class CountryItem(
	val name: String,
	val code: String,
	@DrawableRes val flag: Int,
	val value: String
)
