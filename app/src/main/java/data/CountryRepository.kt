package data

import android.util.Log
import com.example.worlddata.CountryItem
import com.example.worlddata.R
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

// todo: continue from here.



class CountryRepository @Inject constructor(
	private val countryDao: CountryDao
) {


	suspend fun initializeCountries() {



		Log.d("test_db", "initializeCountries!")


		insertCountries(
			listOf(
				Country("United Arab Emirates", R.drawable.uae_flag, 1000, 10),
				Country("Israel", R.drawable.israel_flag, 2000, 10),
				Country("Czech Republic", R.drawable.czech_republic_flag, 3000, 10),
			)
		)

	}


	// Get all countries from the database
	fun getCountries(): Flow<List<Country>> {

		// todo: here I need to check if I need to update the countries from the server. need to add a check.
		return countryDao.getCountries()
	}

	// Insert a list of countries into the database
	private suspend fun insertCountries(countries: List<Country>) {
		countryDao.insertCountries(countries)
	}

	// Delete all countries from the database
	private suspend fun deleteAllCountries() {
		countryDao.deleteAll()
	}
}