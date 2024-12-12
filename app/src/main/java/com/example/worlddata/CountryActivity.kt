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
        val officialNameView: TextView = findViewById(R.id.official_name_view)

        val officialName = country.officialName
        officialNameView.updateVisibility(officialName != null){
            it.text = getString(R.string.official_name, officialName)
        }
    }


    /**
     * Initialize the capital from the given country.
     */
    private fun updateCapital(country: FormattedCountry){
        val capitalTextView: TextView = findViewById(R.id.capital_view)
        capitalTextView.text = getString(R.string.capital, country.capital)
    }


    /**
     * Initialize the language from the given country.
     */
    private fun updateLanguage(country: FormattedCountry){
        val languageTextView: TextView = findViewById(R.id.language_view)
        languageTextView.text = getString(R.string.language, country.languages)
    }


    /**
     * Initialize the population from the given country.
     */
    private fun updatePopulation(country: FormattedCountry){
        val populationTextView: TextView = findViewById(R.id.population_view)
        populationTextView.text = getString(R.string.population, country.population)

    }


    /**
     * Initialize the area from the given country.
     */
    private fun updateArea(country: FormattedCountry){
        val areaTextView: TextView = findViewById(R.id.area_view)
        areaTextView.text = getString(R.string.area, country.area)
    }


    /**
     * Initialize the area from the given country.
     */
    private fun updateContinent(country: FormattedCountry){
        val continentTextView: TextView = findViewById(R.id.continent_view)

        val continents = country.continents
        continentTextView.updateVisibility(continents != null){
            it.text = getString(R.string.located_in, continents)
        }
    }


    /**
     * Initialize the density from the given country.
     */
    private fun updateDensity(country: FormattedCountry){
        val densityTextView: TextView = findViewById(R.id.density_view)
        densityTextView.text = getString(R.string.density, country.density)
    }


    /**
     * Initialize the gdp from the given country.
     */
    private fun updateGdp(country: FormattedCountry){
        val gdpTextView: TextView = findViewById(R.id.gdp_view)
        gdpTextView.text = getString(R.string.gdp, country.gdp)
    }


    /**
     * Initialize the gdp per capita from the given country.
     */
    private fun updateGdpPerCapita(country: FormattedCountry){
        val gdpPCTextView: TextView = findViewById(R.id.gdp_pc_view)
        gdpPCTextView.updateVisibility(!country.gdpPerCapita.isNullOrEmpty()) {
            it.text = getString(R.string.gdp_per_capita, country.gdpPerCapita)
        }
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