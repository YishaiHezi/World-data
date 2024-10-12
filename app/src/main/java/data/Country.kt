package data

import androidx.room.Entity
import androidx.room.PrimaryKey


/**
 * The country data class.
 * The flag is a drawable resource id.
 */
@Entity(tableName = "countries")
data class Country(
	@PrimaryKey val name: String,
	val flag: Int,
	val population: Long,
	val size: Long
)