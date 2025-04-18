package com.example.worlddata

import androidx.annotation.DrawableRes
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import data.CountriesCodes.countryFlagMap
import data.CountryRepository
import data.FormattedCountry
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject


/**
 * The view model for the screen that shows a single country.
 */
@HiltViewModel
class CountryViewModel @Inject constructor(
    countryRepository: CountryRepository,
    savedStateHandle: SavedStateHandle
): ViewModel() {


    /**
     * The country code.
     */
    private val countryCode: String = savedStateHandle["country_code"] ?: throw IllegalArgumentException("Country code missing!")


    /**
     * The flag of the country.
     */
    @DrawableRes val flag: Int = countryFlagMap[countryCode] ?: 0


    /**
     * A flow of the country, from the repository.
     */
    val countryFlow: Flow<FormattedCountry> = countryRepository.getCountry(countryCode).map {
        country -> country.toFormattedCountry()
    }


}