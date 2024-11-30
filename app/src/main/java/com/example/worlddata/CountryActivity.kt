package com.example.worlddata

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.viewModels
import androidx.annotation.DrawableRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import dagger.hilt.android.AndroidEntryPoint
import data.Country
import kotlinx.coroutines.launch


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
    private fun updateUi(country: Country) {
        val flag = viewModel.flag
        val name = country.name

        val flagView: ImageView = findViewById(R.id.flag)
        flagView.setImageResource(flag)

        val nameView: TextView = findViewById(R.id.country_name)
        nameView.text = name
    }


    companion object {


        /**
         * Create a start intent for this activity.
         */
        fun createStartIntent(context: Context, countryCode: String, @DrawableRes flag: Int): Intent {
            val intent = Intent(context, CountryActivity::class.java)
            intent.putExtra("country_code", countryCode)
            intent.putExtra("flag", flag)
            return intent
        }


    }



}