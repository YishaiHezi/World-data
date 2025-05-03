package data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject


/**
 * The repository for providing the countries.
 */
class CountryRepository @Inject constructor(
	private val countryDao: CountryDao
) {


	/**
	 * Get a country by its country code.
	 */
	fun getCountry(countryCode: String): Flow<Country> {
		val rawCountry = countryDao.getCountry(countryCode)

		return rawCountry.map { toCountry(it) }
	}


	/**
	 * Get all the countries from the database
	 */
	fun getCountries(): Flow<List<Country>> {
		return countryDao.getCountries().map { rawCountries -> rawCountries.map(::toCountry) }
	}


	/**
	 * Insert a list of countries into the database
	 */
	private suspend fun insertCountries(countries: List<Country>) {
		val rawCountries = countries.map { toRawCountry(it) }
		countryDao.insertCountries(rawCountries)
	}


	/**
	 * Delete all countries from the database
	 */
	private suspend fun deleteAllCountries() {
		countryDao.deleteAll()
	}


	/**
	 * Converts a raw country to a country.
	 */
	private fun toCountry(rawCountry: RawCountry): Country {
		return Country(
			rawCountry.name,
			rawCountry.countryCode,
			rawCountry.capital,
			rawCountry.population,
			rawCountry.area,
			rawCountry.density,
			rawCountry.gdp,
			rawCountry.gdpPerCapita,
			rawCountry.isUNMember,
			rawCountry.coatOfArms,
			rawCountry.officialName,
			rawCountry.continents,
			rawCountry.currency,
			rawCountry.languages,
			rawCountry.timezones
		)
	}


	/**
	 * Converts a country to a raw country.
	 */
	private fun toRawCountry(country: Country): RawCountry {
		return RawCountry(
			country.name,
			country.countryCode,
			country.capital,
			country.population,
			country.area,
			country.density,
			country.gdp,
			country.gdpPerCapita,
			country.isUNMember,
			country.coatOfArms,
			country.officialName,
			country.continents,
			country.currency,
			country.languages,
			country.timezones
		)
	}

}