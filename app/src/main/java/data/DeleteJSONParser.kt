package data

import android.content.Context
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONArray
import java.io.InputStream


// todo: This file needs to be deleted at the end.


object DeleteJSONParser {

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
    private fun parseJSON(context: Context): List<Country> {
        val countries = mutableListOf<Country>()

        try {
            // Read the JSON file from assets
            val inputStream: InputStream = context.assets.open("countriesData.json")
            val jsonString = inputStream.bufferedReader().use { it.readText() }

            // Parse the JSON data
            val jsonArray = JSONArray(jsonString)
            for (i in 0 until jsonArray.length()) {
                val jsonObject = jsonArray.getJSONObject(i)
                val country = Country(
                    jsonObject.optString("name"),
                    jsonObject.optString("countryCode"),
                    jsonObject.optString("capital"),
                    jsonObject.optInt("population").toDouble(),
                    jsonObject.optInt("area").toDouble(),
                    jsonObject.optInt("population").toDouble() / jsonObject.optInt("area").toDouble(),
                    jsonObject.optDouble("GDP"),
                    jsonObject.optDouble("GDPPerCapita"),
                    jsonObject.optBoolean("isUNMember", false),
                )

                countries.add(country)
            }
        } catch (e: Exception) {
            Log.e("JSONParser", "Error parsing JSON", e)
        }

        return countries
    }

}