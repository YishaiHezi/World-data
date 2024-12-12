package data

import androidx.room.TypeConverter
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json


/**
 * Converters from objects to Strings and vice versa, for the DB.
 */
class Converters {


    /**
     * Converts a list of strings to a JSON string.
     */
    @TypeConverter
    fun fromStringList(value: List<String>?): String? {
        return value?.let { Json.encodeToString(it) }
    }


    /**
     * Converts a JSON string to a list of strings.
     */
    @TypeConverter
    fun toStringList(value: String?): List<String>? {
        return value?.let { Json.decodeFromString(it) }
    }

}
