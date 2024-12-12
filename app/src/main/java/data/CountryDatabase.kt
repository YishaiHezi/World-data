package data

import androidx.room.Database
import androidx.room.RoomDatabase

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
@Database(entities = [RawCountry::class], version = 12, exportSchema = false)
abstract class CountryDatabase : RoomDatabase() {

	/**
	 * Room will implement this method to provide an instance of the CountryDao.
	 * (Room requires that the database class, which extends RoomDatabase,
	 * has one or more abstract methods that return DAO instances. )
	 */
	abstract fun countryDao(): CountryDao
}