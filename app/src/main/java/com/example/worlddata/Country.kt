package com.example.worlddata


/**
 * The country data class.
 * The flag is a drawable resource id.
 */
data class Country(val name: String, val flag: Int, val population: Long, val size: Long)


/**
 * The country item data class. Holds the parameter on which the rating is done.
 */
data class CountryItem(val name: String, val flag: Int, val parameter: Long)
