//package utils
//
//import kotlinx.serialization.*
//import kotlinx.serialization.json.*
//import java.io.File
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
//@Serializable
//data class Country(
//    val name: Name,
//    val cca2: String,
//    val currencies: Map<String, Currency>,
//    val languages: Map<String, String>,
//    val timezones: List<String>,
//    val continents: List<String>
//)
//
//@Serializable
//data class Name(
//    val common: String,
//    val official: String
//)
//
////@Serializable
////data class Currency(
////    val name: String,
////    val symbol: String?
////)
//
//fun main() {
//
//    val externalJson = File("/Users/yishai.hezi/AndroidStudioProjects/world-data/app/src/main/assets/json_response.json").readText()
//    val countriesJson = File("/Users/yishai.hezi/AndroidStudioProjects/world-data/app/src/main/assets/countries_data.json").readText()
//
//    // Parse the JSON
//
//    val json = Json { ignoreUnknownKeys = true } // Allows skipping unknown fields
//
//    val newCountries = json.decodeFromString<List<Country>>(externalJson)
//    val oldCountriesList = json.decodeFromString<MutableList<OldCountry>>(countriesJson)
//
//    // Create a map from country code to the remaining fields
//    val newCountryMap = newCountries.associateBy { it.cca2 }
//
//    oldCountriesList.forEach {
//        val newCountry = newCountryMap[it.countryCode]
//        it.name = newCountry?.name?.common ?: ""
//        it.officialName = newCountry?.name?.official ?: ""
//        it.continents = newCountry?.continents
//        it.currency = newCountry?.currencies?.values?.toList()
//        it.languages = newCountry?.languages?.values?.toList()
//        it.timezones = newCountry?.timezones
//    }
//
//
//
//    val updatedJson = json.encodeToString(oldCountriesList)
//    // Write updated JSON to a file
//    File("/Users/yishai.hezi/AndroidStudioProjects/world-data/app/src/main/assets/updated_countries.json").writeText(updatedJson)
//
//    println("Updated countries written to updated_countries.json")
//
//
//
//}