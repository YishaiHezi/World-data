package utils

import android.content.Context
import data.CountryDao
import data.quiz.QuestionDao
import data.QuestionEntity
import data.RawCountry
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

@Serializable
data class JsonQuestion(
    val imageName: String,
    val questionText: String,
    val options: List<String>,
    val correctAnswer: String,
    val level: Int
)


object DBUpdater {


    /**
     * Insert questions from a JSON file into the database.
     */
    suspend fun insertQuestionsFromJSON(context: Context, questionDao: QuestionDao) {
        // Parse JSON
        val questions = parseQuestionsJSON(context)

        // Insert parsed questions into the database
        questionDao.insertQuestions(questions)
    }


    /**
     * Parse the JSON file and return a list of [QuestionEntity].
     */
    private fun parseQuestionsJSON(context: Context): List<QuestionEntity>{
        val inputStream: InputStream = context.assets.open("questions_data.json")
        val jsonString = inputStream.bufferedReader().use { it.readText() }

        val jsonQuestions = Json.decodeFromString<List<JsonQuestion>>(jsonString)

        return jsonQuestions.map {
            QuestionEntity(
                imageName = it.imageName,
                questionText = it.questionText,
                options = it.options,
                correctAnswer = it.correctAnswer,
                chosenAnswer = null,
                level = it.level
            )
        }
    }


    /**
     * Insert countries from a JSON file into the database.
     */
    suspend fun insertCountriesFromJSON(context: Context, countryDao: CountryDao) {
        // Parse JSON
        val countries = parseCountriesJSON(context)

        // Insert parsed countries into the database
        countryDao.insertCountries(countries)

    }


    /**
     * Parse the JSON file and return a list of [RawCountry].
     */
    private fun parseCountriesJSON(context: Context): List<RawCountry> {
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