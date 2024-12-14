package data

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


/**
 * This is a module that hilt needs in order to know how to create the database and the DAO.
 * "fallbackToDestructiveMigration" - It means that if I change the version of the DB (increase it by 1)
 * and I won't provide a migration logic, it will delete the DB and create it from scratch.
 */
@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

	@Provides
	@Singleton
	fun provideDatabase(@ApplicationContext context: Context): CountryDatabase {
		return DatabaseFactory.createCountryDatabase(context)
	}

	@Provides
	fun provideCountryDao(database: CountryDatabase): CountryDao {
		return database.countryDao()
	}
}