package com.example.worlddata

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import data.CountryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject


/**
 * The view model for the country rating fragment.
 *
 * @author Yishai Hezi
 */
@HiltViewModel
class CountryRatingViewModel @Inject constructor(
	private val countryRepository: CountryRepository
): ViewModel() {


	/**
	 * A flow of the list of countries, from the repository.
	 */
	val countriesFlow: Flow<List<CountryItem>> = countryRepository.getCountries().map {

		// todo: Need to add logic that put the relevant parameter (not population).

		it.map { country ->
			CountryItem(country.name, country.flag, country.population)
		}

	}


}