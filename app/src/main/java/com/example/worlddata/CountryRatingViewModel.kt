package com.example.worlddata

import android.content.Context
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import data.CountryRepository
import data.ParameterType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
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
	 * The selected parameter by the user.
	 */
	private val selectedParameterFlow = MutableStateFlow(ParameterType.POPULATION)


	/**
	 * A flow of the list of countries, from the repository, sorted by the selected parameter.
	 */
	val countriesFlow: Flow<List<CountryItem>> = combine(
		countryRepository.getCountries(),
		selectedParameterFlow
	) {
	  countries, paramType ->
		countries
			.filter { it.isUNMember && it.getParameter(paramType) != null }
			.sortedByDescending { it.getParameter(paramType) }
			.map {
				val country = it.toFormattedCountry()
				CountryItem(
					name = country.name,
					code = country.countryCode,
					flag = countryRepository.countryFlagMap[country.countryCode] ?: 0,
					value = it.getFormattedParameter(paramType) ?: ""
				)
			}
	}


	/**
	 * Set the selected parameter.
	 */
	fun setSelectedParameter(parameter: ParameterType?) {
		selectedParameterFlow.value = parameter ?: ParameterType.POPULATION
	}

}