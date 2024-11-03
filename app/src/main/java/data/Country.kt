package data

import androidx.room.Entity
import androidx.room.PrimaryKey


/**
 * The country data class.
 * The flag is the name of the drawable resource of the country's flag.
 */
@Entity(tableName = "countries")
data class Country(
	@PrimaryKey val name: String,
	val countryCode: String,
	val capital: String,
	val population: Double,
	val area: Double,
	val density: Double = population / area,
	val gdp: Double?,
	val gdpPerCapita: Double?
) {

	/**
	 * Returns the parameter value for the given parameter type.
	 */
	fun getParameter(parameter: ParameterType): Double? {
		return when (parameter) {
			ParameterType.POPULATION -> this.population
			ParameterType.AREA -> this.area
			ParameterType.DENSITY -> this.density
			ParameterType.GDP -> this.gdp
			ParameterType.GDP_PER_CAPITA -> this.gdpPerCapita
			// Add other parameters as needed
		}
	}

}


/**
 * The parameter type enum.
 */
enum class ParameterType {
	POPULATION,
	AREA,
	DENSITY,
	GDP,
	GDP_PER_CAPITA

}