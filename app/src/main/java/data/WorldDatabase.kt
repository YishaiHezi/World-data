package data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import data.quiz.QuestionDao

/**
 * This is a Room Database that stores all the countries and their parameters.
 *
 * "entities": This parameter specifies the list of entity classes associated with the database.
 * An entity represents a table in the database.
 *
 * "version": This parameter defines the version of the database schema.
 * It is an integer value that starts at 1 and should be incremented whenever there are changes to the database schema,
 * for example adding, removing, or modifying tables (adding columns, changing column types, etc.)
 *
 * @author Yishai Hezi
 */
@Database(entities = [RawCountry::class, QuestionEntity::class], version = 18, exportSchema = false)
@TypeConverters(Converters::class)
abstract class WorldDatabase : RoomDatabase() {

	/**
	 * Room will implement this method to provide an instance of the CountryDao.
	 * (Room requires that the database class, which extends RoomDatabase,
	 * has one or more abstract methods that return DAO instances. )
	 */
	abstract fun countryDao(): CountryDao


	/**
	 * Room will implement this method to provide an instance of the QuestionDao.
	 * (Room requires that the database class, which extends RoomDatabase,
	 * has one or more abstract methods that return DAO instances. )
	 */
	abstract fun questionDao(): QuestionDao
}