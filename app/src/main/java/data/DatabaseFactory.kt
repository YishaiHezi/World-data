package data

import android.content.Context
import androidx.room.Room
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import utils.DBUpdater
import androidx.core.content.edit
import data.quiz.QuestionDao


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
        return Room.databaseBuilder(context, WorldDatabase::class.java, "world_database")
            .fallbackToDestructiveMigration()
            .build()
    }


    /**
     * Populate the DB with data from "assets" folder if needed.
     */
    fun populateDB(context: Context, countryDao: CountryDao, questionDao: QuestionDao, forceUpdate: Boolean = false){
        populateTableIfNeeded(context, "is_country_table_populated") {
            DBUpdater.insertCountriesFromJSON(context, countryDao)
        }

        populateTableIfNeeded(context, "is_question_table_populated", forceUpdate) {
            DBUpdater.insertQuestionsFromJSON(context, questionDao)
        }
    }


    /**
     * Populate the database if it is empty.
     */
    private fun populateTableIfNeeded(context: Context, tableKey: String, forceUpdate: Boolean = false, populateBlock: suspend () -> Unit) {
        val sharedPreferences = context.getSharedPreferences("db_prefs", Context.MODE_PRIVATE)

        val isTablePopulated = sharedPreferences.getBoolean(tableKey, false)

        if (!isTablePopulated || forceUpdate) {
            val result = CoroutineScope(Dispatchers.IO).async {
                populateBlock()
            }

            result.invokeOnCompletion { cause ->
                sharedPreferences.edit { putBoolean(tableKey, cause == null) }
            }
        }
    }


}