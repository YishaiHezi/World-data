package utils

import android.content.Context
import data.CountryDao
import data.RawCountry
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import java.io.InputStream

/**
 * This file has the logic for updating the DB from the JSON file (countries_data.json) in assets folder.
 */
// todo: This file needs to be deleted at the end.


@Serializable
data class JsonCurrency(
    val name: String,
    val symbol: String? = null
)


@Serializable
data class JsonCountry(
    val area: Double,
    val capital: String,
    val GDP: Double? = null,
    val countryCode: String,
    val name: String,
    val GDPPerCapita: Double? = null,
    val population: Double,
    val isUNMember: Boolean,
    val coatOfArms: String,
    val officialName: String,
    val continents: List<String>,
    val currency: List<JsonCurrency>,
    val languages: List<String>,
    val timezones: List<String>,
)


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
     * Parse the JSON file and return a list of [RawCountry].
     */
    private fun parseJSON(context: Context): List<RawCountry> {
            val inputStream: InputStream = context.assets.open("countries_data.json")
            val jsonString = inputStream.bufferedReader().use { it.readText() }

            val jsonCountries = Json.decodeFromString<List<JsonCountry>>(jsonString)

            return jsonCountries.map {
                RawCountry(
                    countryCode = it.countryCode,
                    name = it.name,
                    capital = it.capital,
                    population = it.population,
                    area = it.area,
                    density = it.population / it.area,
                    gdp = it.GDP,
                    gdpPerCapita = it.GDPPerCapita,
                    isUNMember = it.isUNMember,
                    coatOfArms = it.coatOfArms,
                    officialName = it.officialName,
                    continents = it.continents,
                    currency = it.currency.map { currency -> currency.name }, // Extract currency names
                    languages = it.languages,
                    timezones = it.timezones
                )
            }



    }

}