package com.example.worlddata.country.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import data.RawCountry
import kotlinx.coroutines.flow.Flow

/**
 * This is an interface to access the "countries" table in the database.
 * DAO = Data Access Object.
 * It encapsulates the logic for accessing data and abstracts
 * away the underlying database implementation details.
 *
 * @author Yishai Hezi
 */
@Dao
interface CountryDao {

	@Query("SELECT * FROM countries")
	fun getCountries(): Flow<List<RawCountry>>

	@Insert(onConflict = OnConflictStrategy.REPLACE)
	suspend fun insertCountries(countries: List<RawCountry>)

	@Query("DELETE FROM countries")
	suspend fun deleteAll()

	@Query("SELECT * FROM countries WHERE countryCode = :code LIMIT 1")
	fun getCountry(code: String): Flow<RawCountry>
}