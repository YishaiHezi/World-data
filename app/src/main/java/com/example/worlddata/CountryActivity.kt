package com.example.worlddata

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity


/**
 * Shows data about a specific country.
 */
class CountryActivity : AppCompatActivity(R.layout.country_activity) {


    // todo: Implement this class.


    companion object {


        /**
         * Create a start intent for this activity.
         */
        fun createStartIntent(context: Context, countryCode: String): Intent {
            val intent = Intent(context, CountryActivity::class.java)
            intent.putExtra("code", countryCode)
            return intent
        }


    }



}