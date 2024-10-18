package data

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


/**
 * This is a module that hilt needs in order to know how to create the database and the DAO.
 */
@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

	@Provides
	@Singleton
	fun provideDatabase(@ApplicationContext context: Context): CountryDatabase {
		return Room.databaseBuilder(
			context,
			CountryDatabase::class.java,
			"country_database" // Name of the database
		).build()
	}

	@Provides
	fun provideCountryDao(database: CountryDatabase): CountryDao {
		return database.countryDao()
	}
}