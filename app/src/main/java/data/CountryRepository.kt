package data

import com.example.worlddata.R
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


/**
 * The repository for providing the countries.
 */
class CountryRepository @Inject constructor(
	private val countryDao: CountryDao
) {


	// todo: need to initialize the repository. if its empty or need an update - need to call the server. maybe it should be in the getCountries method.
	suspend fun initializeCountries() {
		insertCountries(
			listOf(
//				Country("United Arab Emirates", R.drawable.uae_flag, 1000, 10),
//				Country("Israel", R.drawable.israel_flag, 2000, 10),
//				Country("Czech Republic", R.drawable.czech_republic_flag, 3000, 10),
			)
		)

	}


	/**
	 * Get all the countries from the database
	 */
	fun getCountries(): Flow<List<Country>> {
		return countryDao.getCountries()
	}


	/**
	 * Insert a list of countries into the database
	 */
	private suspend fun insertCountries(countries: List<Country>) {
		countryDao.insertCountries(countries)
	}


	/**
	 * Delete all countries from the database
	 */
	private suspend fun deleteAllCountries() {
		countryDao.deleteAll()
	}
}