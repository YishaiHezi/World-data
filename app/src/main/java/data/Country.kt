package data

import utils.CountryFormatter


/**
 * The country data class.
 * The flag is the name of the drawable resource of the country's flag.
 */

data class Country(
	val name: String,
	val countryCode: String,
	val capital: String,
	val population: Double,
	val area: Double,
	val density: Double = population / area,
	val gdp: Double?,
	val gdpPerCapita: Double?,
	val isUNMember: Boolean = false,
	val coatOfArms: String = "",
	val officialName: String? = null,
	val continents: List<String>? = null,
	val currency: List<String>? = null,
	val languages: List<String>? = null,
	val timezones: List<String>? = null
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


	/**
	 * Returns the formatted parameter value for the given parameter type.
	 */
	fun getFormattedParameter(type: ParameterType): String? {
		return when (type) {
			ParameterType.POPULATION -> CountryFormatter.formatPopulation(this.population)
			ParameterType.AREA -> CountryFormatter.formatArea(this.area)
			ParameterType.DENSITY -> CountryFormatter.formatDensity(this.density)
			ParameterType.GDP -> CountryFormatter.formatGDP(this.gdp)
			ParameterType.GDP_PER_CAPITA -> CountryFormatter.formatGDPPerCapita(this.gdpPerCapita)
			// Add other parameters as needed
		}
	}


	/**
	 * Converts the country to a formatted country.
	 */
	fun toFormattedCountry(): FormattedCountry{
		return FormattedCountry(
			name = this.name,
			countryCode = this.countryCode,
			capital = this.capital,
			population = CountryFormatter.formatPopulation(this.population),
			area = CountryFormatter.formatArea(this.area),
			density = CountryFormatter.formatDensity(this.density),
			gdp = CountryFormatter.formatGDP(this.gdp),
			gdpPerCapita = CountryFormatter.formatGDPPerCapita(this.gdpPerCapita),
			coatOfArms = this.coatOfArms,
			languages = this.languages?.joinToString(separator = ", "),
			officialName = this.officialName
		)
	}



}


/**
 * The formatted country data class.
 */
data class FormattedCountry(
	val name: String,
	val countryCode: String,
	val capital: String,
	val population: String,
	val area: String,
	val density: String,
	val gdp: String?,
	val gdpPerCapita: String?,
	val coatOfArms: String = "",
	val languages: String?,
	val officialName: String? = null
)


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