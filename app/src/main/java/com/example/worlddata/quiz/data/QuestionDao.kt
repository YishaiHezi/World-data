package com.example.worlddata.quiz.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import data.QuestionEntity


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

    @Query("SELECT * FROM questions")
    fun getAllQuestions(): List<QuestionEntity>

    @Query("SELECT * FROM questions WHERE chosenAnswer IS NULL")
    fun getAllNotAnsweredQuestions(): List<QuestionEntity>

    @Query("SELECT * FROM questions WHERE id = :id")
    fun getQuestion(id: String): QuestionEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertQuestion(question: QuestionEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertQuestions(questions: List<QuestionEntity>)

    @Query("DELETE FROM questions")
    suspend fun deleteAll()
}