package data

import android.content.Context
import androidx.room.Room
import data.DatabaseModule.provideCountryDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import utils.DBUpdater
import androidx.core.content.edit
import data.DatabaseModule.provideQuestionDao


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

        populateTableIfNeeded(context, "is_country_table_populated") {
            DBUpdater.insertCountriesFromJSON(context, provideCountryDao(db))
        }

        populateTableIfNeeded(context, "is_question_table_populated") {
            DBUpdater.insertQuestionsFromJSON(context, provideQuestionDao(db))
        }

        return db
    }


    /**
     * Populate the database if it is empty.
     */
    private fun populateTableIfNeeded(context: Context, tableKey: String, populateBlock: suspend () -> Unit) {
        val sharedPreferences = context.getSharedPreferences("db_prefs", Context.MODE_PRIVATE)

        val isTablePopulated = sharedPreferences.getBoolean(tableKey, false)

        if (!isTablePopulated) {
            val result = CoroutineScope(Dispatchers.IO).async {
                populateBlock()
            }

            result.invokeOnCompletion { cause ->
                sharedPreferences.edit { putBoolean(tableKey, cause == null) }
            }
        }
    }


}