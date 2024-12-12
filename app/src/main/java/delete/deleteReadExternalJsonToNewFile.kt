//package utils
//
//import kotlinx.serialization.*
//import kotlinx.serialization.json.*
//import kotlinx.serialization.encodeToString
//import java.io.File
//
//@Serializable
//data class CountryResponse(
//    val name: NameData,
//    val cca2: String
//)
//
//@Serializable
//data class NameData(
//    val common: String? = null,
//    val official: String? = null,
////    val nativeName: Map<String, NativeNameData>? = null
//)
//
//@Serializable
//data class NewCountry(
//    val countryCode: String,
//    var name: String,
//    var officialName: String? = null, // new
//    val area: Double,
//    val population: Double,
//    val capital: String,
//    val gdp: Double? = null,
//    val gdpPerCapita: Double? = null,
//    val isUNMember: Boolean,
//    var coatOfArms: String? = null,
//
//
//    var continents: List<String>? = null, // new
//    var currency: List<Currency>? = null, // new
//    var languages: List<String>? = null, // new
//    var timezones: List<String>? = null, // new
//)
//
//
//@Serializable
//data class OldCountry(
//    val area: Double,
//    val capital: String,
//    val GDP: Double? = null,
//    val countryCode: String,
//    var name: String,
//    val GDPPerCapita: Double? = null,
//    val population: Double,
//    val isUNMember: Boolean,
//    var coatOfArms: String? = null, // New field for coat of arms
//
//
//    var officialName: String? = null, // new
//    var continents: List<String>? = null, // new
//    var currency: List<Currency>? = null, // new
//    var languages: List<String>? = null, // new
//    var timezones: List<String>? = null, // new
//)
//
//
///**
// * This file has the logic for creating a new JSON file to be in assets folder
// * (instead of countries_data.json, I can just copy the content of the new file to countries_data.json),
// * from another JSON file (json_response.json).
// */
//fun main() {
//    // Read JSON files
//    val externalJson = File("/Users/yishai.hezi/AndroidStudioProjects/world-data/app/src/main/assets/json_response.json").readText()
//    val countriesJson = File("/Users/yishai.hezi/AndroidStudioProjects/world-data/app/src/main/assets/countries_data.json").readText()
//
//    // Parse JSON files
//    val json = Json { ignoreUnknownKeys = true } // Allows skipping unknown fields
//
//
//    //////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
//    val newCountriesList = json.decodeFromString<List<CountryResponse>>(externalJson)
//    val countriesList = json.decodeFromString<MutableList<NewCountry>>(countriesJson)
//
//    // Create a map of coat of arms by country code
//    val newCountriesMap = newCountriesList.associateBy { it.cca2 }
//
//    // Update countries with coat of arms data
//    countriesList.forEach { country ->
////        country.coatOfArms = newCountriesMap[country.countryCode]?.coatOfArms?.png
//    }
//
//    //////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
//    // Serialize updated list back to JSON
//    val updatedJson = json.encodeToString(countriesList)
//
//    // Write updated JSON to a file
//    File("/Users/yishai.hezi/AndroidStudioProjects/world-data/app/src/main/assets/updated_countries.json").writeText(updatedJson)
//
//    println("Updated countries written to updated_countries.json")
//}
