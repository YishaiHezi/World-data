package utils

import java.text.DecimalFormat

/**
 * The country formatter. Used to format the country data.
 */
object CountryFormatter {

    /**
     * Format numbers with a "comma" separating thousands.
     */
    private val numberFormat = DecimalFormat("#,###")


    /**
     * Format large numbers. Like this: "12.4M", "455.7B", "1.2T".
     */
    private val largeNumberFormat = DecimalFormat("#.#")


    /**
     * Format the area: "123,456 km²".
     */
    fun formatArea(area: Double): String {
        return "${numberFormat.format(area)} km\u00B2"
    }


    /**
     * Format the population.
     */
    fun formatPopulation(population: Double): String {
        return when {
            population >= 1_000_000_000 -> "${largeNumberFormat.format(population / 1_000_000_000)}B"
            population >= 1_000_000 -> "${largeNumberFormat.format(population / 1_000_000)}M"
            population >= 1_000 -> numberFormat.format(population)
            else -> numberFormat.format(population)
        }
    }


    /**
     * Format the density: 255 people/km²
     */
    fun formatDensity(density: Double): String {
        return "${numberFormat.format(density)} people/km\u00B2"
    }


    /**
     * Format the GDP: "$344.4B"
     */
    fun formatGDP(gdp: Double?): String? {
        return gdp?.let {
            when {
                it >= 1_000_000_000_000 -> "$${largeNumberFormat.format(it / 1_000_000_000_000)}T"
                it >= 1_000_000_000 -> "$${largeNumberFormat.format(it / 1_000_000_000)}B"
                it >= 1_000_000 -> "$${largeNumberFormat.format(it / 1_000_000)}M"
                else -> "$${numberFormat.format(it)}"
            }
        }
    }


    /**
     * Format the GDP per capita.
     */
    fun formatGDPPerCapita(gdpPerCapita: Double?): String? {
        return gdpPerCapita?.let { "$${numberFormat.format(it)}" }
    }


}