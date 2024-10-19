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
) {

	/**
	 * Returns the parameter value for the given parameter type.
	 */
	fun getParameter(parameter: ParameterType): Long {
		return when (parameter) {
			ParameterType.POPULATION -> this.population
			ParameterType.SIZE -> this.size
			// Add other parameters as needed
		}
	}

}


/**
 * The parameter type enum.
 */
enum class ParameterType {
	POPULATION,
	SIZE
}