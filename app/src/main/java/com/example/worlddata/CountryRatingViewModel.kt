package com.example.worlddata

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


/**
 * The view model for the country rating fragment.
 *
 * @author Yishai Hezi
 */
class CountryRatingViewModel : ViewModel() {


	/**
	 * A flow of the list of countries. // todo: change it to a real flow that gets data from the data layer.
	 */
	val countriesFlow: Flow<List<CountryItem>> = flow {
		emit(
			listOf(
				CountryItem("United Arab Emirates", R.drawable.uae_flag, 1000),
				CountryItem("Israel", R.drawable.israel_flag, 2000),
				CountryItem("Canada", R.drawable.canada_flag, 3000),
				CountryItem("Japan", R.drawable.japan_flag, 4000),
			)
		)
	}


}