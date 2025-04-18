package data

import android.content.Context
import androidx.room.Room
import data.DatabaseModule.provideCountryDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import utils.UpdateDBFromJSON


/**
 * This is a factory class that creates a database.
 * "fallbackToDestructiveMigration" - It means that if I change the version of the DB (increase it by 1)
 * and I won't provide a migration logic, it will delete the DB and create it from scratch.
 */
object DatabaseFactory {


    /**
     * Create the country database.
     */
    fun createCountryDatabase(context: Context): WorldDatabase {
        val db = Room.databaseBuilder(context, WorldDatabase::class.java, "world_database")
            .fallbackToDestructiveMigration()
            .build()

        populateDBIfNeeded(context, db, "is_country_table_populated")

        return db
    }


    /**
     * Populate the database if it is empty.
     */
    private fun populateDBIfNeeded(context: Context, db: WorldDatabase, preferencesKey: String) {
        val sharedPreferences = context.getSharedPreferences("db_prefs", Context.MODE_PRIVATE)

        val isDbPopulated = sharedPreferences.getBoolean(preferencesKey, false)

        if (!isDbPopulated) {
            val result = CoroutineScope(Dispatchers.IO).async {
                UpdateDBFromJSON.readCountriesFromJSONToDB(context, provideCountryDao(db))
            }

            result.invokeOnCompletion { cause ->
                if (cause == null)
                    sharedPreferences.edit().putBoolean(preferencesKey, true).apply()
                else
                    sharedPreferences.edit().putBoolean(preferencesKey, false).apply()
            }

        }
    }


}