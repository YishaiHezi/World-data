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

      countries, parameter ->
		countries
			.filter { it.isUNMember }
			.mapNotNull { country ->

            country.getParameter(parameter)?.let { parameterValue ->
                CountryItem(
                    name = country.name,
                    flag = countryRepository.countryFlagMap[country.countryCode] ?: 0,
                    parameter = parameterValue
                )
            }
        }.sortedByDescending { it.parameter }
	}


// todo: uncomment this for updating the DB. Delete at the end.
//	fun startDb(context: Context){
//		countryRepository.populateDB(context)
//	}


	/**
	 * Set the selected parameter.
	 */
	fun setSelectedParameter(parameter: ParameterType) {
		selectedParameterFlow.value = parameter
	}

}