package data

import kotlinx.serialization.*
import kotlinx.serialization.json.*
import kotlinx.serialization.encodeToString
import java.io.File

@Serializable
data class CoatOfArmsResponse(
    val coatOfArms: CoatOfArmsData,
    val cca2: String
)

@Serializable
data class CoatOfArmsData(
    val png: String? = null,
    val svg: String? = null
)

@Serializable
data class Country2(
    val area: Double,
    val capital: String,
    val GDP: Double? = null,
    val countryCode: String,
    val name: String,
    val GDPPerCapita: Double? = null,
    val population: Double,
    val isUNMember: Boolean,
    var coatOfArms: String? = null // New field for coat of arms
)


/**
 * This file has the logic for creating a new JSON file to be in assets folder
 * (instead of countriesData.json, I can just copy the content of the new file to countriesData.json),
 * from another JSON file (json_response.json).
 */
fun main() {
    // Read JSON files
    val coatOfArmsJson = File("/Users/yishai.hezi/AndroidStudioProjects/world-data/app/src/main/assets/json_response.json").readText()
    val countriesJson = File("/Users/yishai.hezi/AndroidStudioProjects/world-data/app/src/main/assets/countriesData.json").readText()

    // Parse JSON files
    val json = Json { ignoreUnknownKeys = true } // Allows skipping unknown fields
    val coatOfArmsList = json.decodeFromString<List<CoatOfArmsResponse>>(coatOfArmsJson)
    val countriesList = json.decodeFromString<MutableList<Country2>>(countriesJson)

    // Create a map of coat of arms by country code
    val coatOfArmsMap = coatOfArmsList.associateBy { it.cca2 }

    // Update countries with coat of arms data
    countriesList.forEach { country ->
        country.coatOfArms = coatOfArmsMap[country.countryCode]?.coatOfArms?.svg
    }

    // Serialize updated list back to JSON
    val updatedJson = json.encodeToString(countriesList)

    // Write updated JSON to a file
    File("/Users/yishai.hezi/AndroidStudioProjects/world-data/app/src/main/assets/updated_countries.json").writeText(updatedJson)

    println("Updated countries written to updated_countries.json")
}
