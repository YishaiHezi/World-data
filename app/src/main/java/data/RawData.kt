package data

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * This file contains data classes for the DB. Each table in the DB save a specific data class from here.
 */


/**
 * This class represents a country in the DB.
 */
@Entity(tableName = "countries")
data class RawCountry(
    @PrimaryKey val countryCode: String,
    val name: String,
    val capital: String,
    val population: Double,
    val area: Double,
    val density: Double = population / area,
    val gdp: Double?,
    val gdpPerCapita: Double?,
    val isUNMember: Boolean = false,
    val coatOfArms: String = "",
    var officialName: String? = null,
    var continents: List<String>? = null,
    var currency: List<String>? = null,
    var languages: List<String>? = null,
    var timezones: List<String>? = null,
)


/**
 * This class represents a question in the DB.
 */
@Entity(tableName = "questions")
data class QuestionEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val imageName: String,
    val questionText: String,
    val options: List<String>,
    val correctAnswer: String,
    var chosenAnswer: String?,
    val level: Int
)