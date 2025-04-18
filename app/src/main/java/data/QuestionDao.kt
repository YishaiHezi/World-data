package data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query


/**
 * This is an interface to access the "questions" table in the database.
 * DAO = Data Access Object.
 * It encapsulates the logic for accessing data and abstracts
 * away the underlying database implementation details.
 *
 * @author Yishai Hezi
 */
@Dao
interface QuestionDao {

    @Query("SELECT * FROM questions WHERE id = :id and chosenAnswer != null")
    fun getQuestion(id: String): QuestionEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertQuestion(question: QuestionEntity)

    @Query("DELETE FROM questions")
    suspend fun deleteAll()
}