package delete

import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File


/**
 * This file has the logic for updating the JSON file in assets folder.
 */
// todo: This file needs to be deleted at the end.


// Define your data class
@Serializable
data class Country1(
    val area: Int? = null,
    val capital: String? = null,
    val GDP: Double? = null,
    val countryCode: String,
    val name: String,
    val GDPPerCapita: Double? = null,
    val population: Int? = null,
    var isUNMember: Boolean? = null // New field to store UN membership status
)



fun main() {
    // Your set of UN member country codes
    val unMembers = setOf(
        "AF", "AL", "DZ", "AD", "AO", "AG", "AR", "AM", "AU", "AT", "AZ", "BS", "BH", "BD", "BB", "BY", "BE", "BZ",
        "BJ", "BT", "BO", "BA", "BW", "BR", "BN", "BG", "BF", "BI", "CV", "KH", "CM", "CA", "CF", "TD", "CL", "CN",
        "CO", "KM", "CG", "CD", "CR", "CI", "HR", "CU", "CY", "CZ", "DK", "DJ", "DM", "DO", "EC", "EG", "SV", "GQ",
        "ER", "EE", "SZ", "ET", "FJ", "FI", "FR", "GA", "GM", "GE", "DE", "GH", "GR", "GD", "GT", "GN", "GW", "GY",
        "HT", "HN", "HU", "IS", "IN", "ID", "IR", "IQ", "IE", "IL", "IT", "JM", "JP", "JO", "KZ", "KE", "KI", "KP",
        "KR", "KW", "KG", "LA", "LV", "LB", "LS", "LR", "LY", "LI", "LT", "LU", "MG", "MW", "MY", "MV", "ML", "MT",
        "MH", "MR", "MU", "MX", "FM", "MD", "MC", "MN", "ME", "MA", "MZ", "MM", "NA", "NR", "NP", "NL", "NZ", "NI",
        "NE", "NG", "MK", "NO", "OM", "PK", "PW", "PA", "PG", "PY", "PE", "PH", "PL", "PT", "QA", "RO", "RU", "RW",
        "KN", "LC", "VC", "WS", "SM", "ST", "SA", "SN", "RS", "SC", "SL", "SG", "SK", "SI", "SB", "SO", "ZA", "SS",
        "ES", "LK", "SD", "SR", "SE", "CH", "SY", "TJ", "TZ", "TH", "TL", "TG", "TO", "TT", "TN", "TR", "TM", "TV",
        "UG", "UA", "AE", "GB", "US", "UY", "UZ", "VU", "VE", "VN", "YE", "ZM", "ZW", "PS", "VA"
    )


    val filePath = "/Users/yishai.hezi/AndroidStudioProjects/world-data/app/src/main/assets/countries_data.json"

    // Read JSON content from the file
    val jsonContent = File(filePath).readText()

    // Deserialize the JSON into a list of Country objects
    val countries = Json.decodeFromString<List<Country1>>(jsonContent).map { country1 ->
        // Update each country with the isUNMember field

        if (unMembers.contains(country1.countryCode))
            return@map Country1(country1.area, country1.capital, country1.GDP, country1.countryCode, country1.name, country1.GDPPerCapita, country1.population, true)
        else
            return@map Country1(country1.area, country1.capital, country1.GDP, country1.countryCode, country1.name, country1.GDPPerCapita, country1.population, false)
    }



    // Serialize the updated list back to JSON format
    val updatedJsonContent = Json.encodeToString(countries)

    // Write the updated JSON content back to the file
    File(filePath).writeText(updatedJsonContent)

    println("JSON file has been updated with the isUNMember field.")
}