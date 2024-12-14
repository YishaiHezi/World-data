package com.example.worlddata

import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import dagger.hilt.android.AndroidEntryPoint
import data.FormattedCountry
import kotlinx.coroutines.launch
import utils.updateVisibility


/**
 * Shows data about a specific country.
 */
@AndroidEntryPoint
class CountryActivity : AppCompatActivity(R.layout.country_activity) {


    /**
     * The view model for this activity.
     */
    private val viewModel: CountryViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.countryFlow.collect { country ->
                    updateUi(country)
                }
            }
        }
    }


    /**
     * Update the UI from the given country.
     */
    private fun updateUi(country: FormattedCountry) {
        updateFlag()
        updateCoatOfArms(country)
        updateName(country)
        updateOfficialName(country)
        updateCapital(country)
        updateLanguage(country)
        updatePopulation(country)
        updateArea(country)
        updateContinent(country)
        updateTimezones(country)
        updateDensity(country)
        updateGdp(country)
        updateGdpPerCapita(country)
    }


    /**
     * Initialize the flag image from the given flag resource.
     */
    private fun updateFlag(){
        val flag = viewModel.flag
        val flagView: ImageView = findViewById(R.id.flag)

        flagView.setImageResource(flag)
        flagView.setOnClickListener {
            FullScreenImageDialogFragment.newInstance(flag).show(supportFragmentManager, "fullScreenImageDialog")
        }
    }


    /**
     * Initialize the coat of arms image and name from the given coat of arms url.
     */
    private fun updateCoatOfArms(country: FormattedCountry) {
        val coatOfArmsUrl = country.coatOfArms
        val coatOfArmsView: ImageView = findViewById(R.id.coat_of_arms)
        val coatOfArmsNameView: TextView = findViewById(R.id.coat_of_arms_name)

        loadCoatOfArms(coatOfArmsView, coatOfArmsNameView, coatOfArmsUrl)
        coatOfArmsView.setOnClickListener {
            FullScreenImageDialogFragment.newInstance(coatOfArmsUrl).show(supportFragmentManager, "fullScreenImageDialog")
        }
    }


    /**
     * Initialize the coat of arms image and name from the given country.
     */
    private fun loadCoatOfArms(coatOfArmsView: ImageView, coatOfArmsNameView: TextView, coatOfArmsUrl: String) {
        Glide.with(this)
            .load(coatOfArmsUrl)
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
                    coatOfArmsView.visibility = View.GONE
                    coatOfArmsNameView.visibility = View.GONE
                    return false
                }

                override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                    coatOfArmsView.visibility = View.VISIBLE
                    coatOfArmsNameView.visibility = View.VISIBLE
                    return false
                }

            })
            .into(coatOfArmsView)
    }


    /**
     * Initialize the country name from the given country.
     */
    private fun updateName(country: FormattedCountry){
        val nameView: TextView = findViewById(R.id.country_name)
        nameView.text = country.name
    }


    /**
     * Initialize the country official name from the given country.
     */
    private fun updateOfficialName(country: FormattedCountry){
        val officialNameHeadline: TextView = findViewById(R.id.official_name_headline)
        val officialNameView: TextView = findViewById(R.id.official_name_view)
        updateData(officialNameHeadline, officialNameView, data = country.officialName)
    }


    /**
     * Initialize the capital from the given country.
     */
    private fun updateCapital(country: FormattedCountry){
        val capitalHeadline: TextView = findViewById(R.id.capital_headline)
        val capitalTextView: TextView = findViewById(R.id.capital_view)
        updateData(capitalHeadline, capitalTextView, data = country.capital)
    }


    /**
     * Initialize the language from the given country.
     */
    private fun updateLanguage(country: FormattedCountry){
        val languageHeadline: TextView = findViewById(R.id.language_headline)
        val languageTextView: TextView = findViewById(R.id.language_view)
        updateData(languageHeadline, languageTextView, data = country.languages)
    }


    /**
     * Initialize the population from the given country.
     */
    private fun updatePopulation(country: FormattedCountry){
        val populationHeadline: TextView = findViewById(R.id.population_headline)
        val populationTextView: TextView = findViewById(R.id.population_view)
        updateData(populationHeadline, populationTextView, data = country.population)
    }


    /**
     * Initialize the area from the given country.
     */
    private fun updateArea(country: FormattedCountry){
        val areaHeadline: TextView = findViewById(R.id.area_headline)
        val areaTextView: TextView = findViewById(R.id.area_view)
        updateData(areaHeadline, areaTextView, data = country.area)
    }


    /**
     * Initialize the continents from the given country.
     */
    private fun updateContinent(country: FormattedCountry){
        val continentHeadline: TextView = findViewById(R.id.continent_headline)
        val continentTextView: TextView = findViewById(R.id.continent_view)
        updateData(continentHeadline, continentTextView, data = country.continents)
    }


    /**
     * Initialize the timezones from the given country.
     */
    private fun updateTimezones(country: FormattedCountry){
        val timezonesHeadline: TextView = findViewById(R.id.timezones_headline)
        val timezonesTextView: TextView = findViewById(R.id.timezones_view)
        updateData(timezonesHeadline, timezonesTextView, data = country.timezones)
    }


    /**
     * Initialize the density from the given country.
     */
    private fun updateDensity(country: FormattedCountry){
        val densityHeadline: TextView = findViewById(R.id.density_headline)
        val densityTextView: TextView = findViewById(R.id.density_view)
        updateData(densityHeadline, densityTextView, data = country.density)
    }


    /**
     * Initialize the gdp from the given country.
     */
    private fun updateGdp(country: FormattedCountry){
        val gdpHeadline: TextView = findViewById(R.id.gdp_headline)
        val gdpTextView: TextView = findViewById(R.id.gdp_view)
        updateData(gdpHeadline, gdpTextView, data = country.gdp)
    }


    /**
     * Initialize the gdp per capita from the given country.
     */
    private fun updateGdpPerCapita(country: FormattedCountry){
        val gdpPCHeadline: TextView = findViewById(R.id.gdp_pc_headline)
        val gdpPCTextView: TextView = findViewById(R.id.gdp_pc_view)
        updateData(gdpPCHeadline, gdpPCTextView, data = country.gdpPerCapita)
    }


    /**
     * Update the headline and subtitle views with the given data.
     */
    private fun updateData(headlineView: TextView, subtitleView: TextView, data: String){
        subtitleView.text = data
        updateVisibility(headlineView, subtitleView, condition = data.isNotEmpty())
    }


    companion object {


        /**
         * Create a start intent for this activity.
         */
        fun createStartIntent(context: Context, countryCode: String): Intent {
            val intent = Intent(context, CountryActivity::class.java)
            intent.putExtra("country_code", countryCode)
            return intent
        }


    }



}