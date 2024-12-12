package data

import androidx.room.Entity
import androidx.room.PrimaryKey


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