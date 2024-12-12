package utils

import android.content.Context
import android.util.Log
import data.Country
import data.CountryDao
import data.RawCountry
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONArray
import java.io.InputStream

/**
 * This file has the logic for updating the DB from the JSON file (countries_data.json) in assets folder.
 */
// todo: This file needs to be deleted at the end.


object UpdateDBFromJSON {

    /**
     * Read countries from a JSON file and insert them into the database.
     */
    fun readCountriesFromJSONToDB(context: Context, countryDao: CountryDao){
        insertCountriesFromJSON(context, countryDao)
    }


    /**
     * Insert countries from a JSON file into the database.
     */
    private fun insertCountriesFromJSON(context: Context, countryDao: CountryDao) {
        CoroutineScope(Dispatchers.IO).launch {
            // Parse JSON
            val countries = parseJSON(context)

            // Insert parsed countries into the database
            countryDao.insertCountries(countries)
        }
    }


    /**
     * Parse the JSON file and return a list of countries.
     */
    private fun parseJSON(context: Context): List<RawCountry> {
        val countries = mutableListOf<RawCountry>()

        try {
            // Read the JSON file from assets
            val inputStream: InputStream = context.assets.open("countries_data.json")
            val jsonString = inputStream.bufferedReader().use { it.readText() }

            // Parse the JSON data
            val jsonArray = JSONArray(jsonString)
            for (i in 0 until jsonArray.length()) {
                val jsonObject = jsonArray.getJSONObject(i)
                val country = RawCountry(
                    jsonObject.optString("countryCode"),
                    jsonObject.optString("name"),
                    jsonObject.optString("capital"),
                    jsonObject.optDouble("population"),
                    jsonObject.optDouble("area"),
                    jsonObject.optDouble("population") / jsonObject.optDouble("area"),
                    jsonObject.optDouble("GDP"),
                    jsonObject.optDouble("GDPPerCapita"),
                    jsonObject.optBoolean("isUNMember", false),
                    jsonObject.optString("coatOfArms"),
                )

                countries.add(country)
            }
        } catch (e: Exception) {
            Log.e("JSONParser", "Error parsing JSON", e)
        }

        return countries
    }

}