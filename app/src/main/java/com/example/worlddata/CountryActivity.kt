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
        val flag = viewModel.flag
        val name = country.name

        val flagView: ImageView = findViewById(R.id.flag)
        initFlag(flagView, flag)

        val coatOfArmsView: ImageView = findViewById(R.id.coat_of_arms)
        val coatOfArmsNameView: TextView = findViewById(R.id.coat_of_arms_name)
        initCoatOfArms(coatOfArmsView, coatOfArmsNameView, country.coatOfArms)

        val nameView: TextView = findViewById(R.id.country_name)
        nameView.text = name

        val capitalTextView: TextView = findViewById(R.id.capital_view)
        capitalTextView.text = getString(R.string.capital, country.capital)

        val populationTextView: TextView = findViewById(R.id.population_view)
        populationTextView.text = getString(R.string.population, country.population)

        val areaTextView: TextView = findViewById(R.id.area_view)
        areaTextView.text = getString(R.string.area, country.area)

        val densityTextView: TextView = findViewById(R.id.density_view)
        densityTextView.text = getString(R.string.density, country.density)

        val gdpTextView: TextView = findViewById(R.id.gdp_view)
        gdpTextView.updateVisibility(!country.gdp.isNullOrEmpty()) {
            it.text = getString(R.string.gdp, country.gdp)
        }

        val gdpPCTextView: TextView = findViewById(R.id.gdp_pc_view)
        gdpPCTextView.updateVisibility(!country.gdpPerCapita.isNullOrEmpty()) {
            it.text = getString(R.string.gdp_per_capita, country.gdpPerCapita)
        }
    }


    /**
     * Initialize the flag image from the given flag resource.
     */
    private fun initFlag(flagView: ImageView, flag: Int){
        flagView.setImageResource(flag)
        flagView.setOnClickListener {
            FullScreenImageDialogFragment.newInstance(flag).show(supportFragmentManager, "fullScreenImageDialog")
        }
    }


    /**
     * Initialize the coat of arms image and name from the given coat of arms url.
     */
    private fun initCoatOfArms(coatOfArmsView: ImageView, coatOfArmsNameView: TextView, coatOfArmsUrl: String) {
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