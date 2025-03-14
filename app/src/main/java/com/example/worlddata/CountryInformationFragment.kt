package com.example.worlddata

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import data.Country
import data.FormattedCountry
import utils.updateVisibility


/**
 * Shows text information about a country.
 */
class CountryInformationFragment : Fragment(R.layout.country_information_fragment) {


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val country: FormattedCountry = arguments?.getParcelable("country") ?: throw IllegalArgumentException("Did you used newInstance?")
        updateUi(view, country)
    }


    /**
     * Update the UI from the given country.
     */
    private fun updateUi(view: View, country: FormattedCountry) {
        updateOfficialName(view, country)
        updateCapital(view, country)
        updateLanguage(view, country)
        updatePopulation(view, country)
        updateArea(view, country)
        updateContinent(view, country)
        updateTimezones(view, country)
        updateDensity(view, country)
        updateGdp(view, country)
        updateGdpPerCapita(view, country)
    }


    /**
     * Initialize the country official name from the given country.
     */
    private fun updateOfficialName(view: View, country: FormattedCountry){
        val officialNameHeadline: TextView = view.findViewById(R.id.official_name_headline)
        val officialNameView: TextView = view.findViewById(R.id.official_name_view)
        updateData(officialNameHeadline, officialNameView, data = country.officialName)
    }


    /**
     * Initialize the capital from the given country.
     */
    private fun updateCapital(view: View, country: FormattedCountry){
        val capitalHeadline: TextView = view.findViewById(R.id.capital_headline)
        val capitalTextView: TextView = view.findViewById(R.id.capital_view)
        updateData(capitalHeadline, capitalTextView, data = country.capital)
    }


    /**
     * Initialize the language from the given country.
     */
    private fun updateLanguage(view: View, country: FormattedCountry){
        val languageHeadline: TextView = view.findViewById(R.id.language_headline)
        val languageTextView: TextView = view.findViewById(R.id.language_view)
        updateData(languageHeadline, languageTextView, data = country.languages)
    }


    /**
     * Initialize the population from the given country.
     */
    private fun updatePopulation(view: View, country: FormattedCountry){
        val populationHeadline: TextView = view.findViewById(R.id.population_headline)
        val populationTextView: TextView = view.findViewById(R.id.population_view)
        updateData(populationHeadline, populationTextView, data = country.population)
    }


    /**
     * Initialize the area from the given country.
     */
    private fun updateArea(view: View, country: FormattedCountry){
        val areaHeadline: TextView = view.findViewById(R.id.area_headline)
        val areaTextView: TextView = view.findViewById(R.id.area_view)
        updateData(areaHeadline, areaTextView, data = country.area)
    }


    /**
     * Initialize the continents from the given country.
     */
    private fun updateContinent(view: View, country: FormattedCountry){
        val continentHeadline: TextView = view.findViewById(R.id.continent_headline)
        val continentTextView: TextView = view.findViewById(R.id.continent_view)
        updateData(continentHeadline, continentTextView, data = country.continents)
    }


    /**
     * Initialize the timezones from the given country.
     */
    private fun updateTimezones(view: View, country: FormattedCountry){
        val timezonesHeadline: TextView = view.findViewById(R.id.timezones_headline)
        val timezonesTextView: TextView = view.findViewById(R.id.timezones_view)
        updateData(timezonesHeadline, timezonesTextView, data = country.timezones)
    }


    /**
     * Initialize the density from the given country.
     */
    private fun updateDensity(view: View, country: FormattedCountry){
        val densityHeadline: TextView = view.findViewById(R.id.density_headline)
        val densityTextView: TextView = view.findViewById(R.id.density_view)
        updateData(densityHeadline, densityTextView, data = country.density)
    }


    /**
     * Initialize the gdp from the given country.
     */
    private fun updateGdp(view: View, country: FormattedCountry){
        val gdpHeadline: TextView = view.findViewById(R.id.gdp_headline)
        val gdpTextView: TextView = view.findViewById(R.id.gdp_view)
        updateData(gdpHeadline, gdpTextView, data = country.gdp)
    }


    /**
     * Initialize the gdp per capita from the given country.
     */
    private fun updateGdpPerCapita(view: View, country: FormattedCountry){
        val gdpPCHeadline: TextView = view.findViewById(R.id.gdp_pc_headline)
        val gdpPCTextView: TextView = view.findViewById(R.id.gdp_pc_view)
        updateData(gdpPCHeadline, gdpPCTextView, data = country.gdpPerCapita)
    }


    /**
     * Update the headline and subtitle views with the given data.
     */
    private fun updateData(headlineView: TextView, subtitleView: TextView, data: String){
        subtitleView.text = data
        updateVisibility(headlineView, subtitleView, condition = data.isNotEmpty())
    }


    companion object{


        /**
         * Creates a new instance of the fragment.
         */
        fun newInstance(country: Country): CountryInformationFragment {
            val fragment = CountryInformationFragment()
            val args = Bundle()
            args.putParcelable("country", country.toFormattedCountry())
            fragment.arguments = args

            return fragment
        }


    }


}