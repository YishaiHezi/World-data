package data

import android.util.Log
import data.Question.Companion.toQuestion
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject


/**
 * Expose quiz questions from the DB to the UI.
 */
class QuizRepository @Inject constructor(private val questionDao: QuestionDao) {


    /**
     * All the quiz questions that can be presented to the user.
     */
    private var allQuestions: List<QuestionEntity> = emptyList()


    /**
     * The index of the next question the user should answer.
     */
    private var nextQuestionIndex = 0


    /**
     * Load all questions from the DB.
     */
    suspend fun loadAllQuestions() = withContext(Dispatchers.IO) {
        allQuestions = questionDao.getAllNotAnsweredQuestions()
        nextQuestionIndex = 0

        Log.d(TAG, "loaded all relevant quiz questions: $allQuestions")
    }


    /**
     * Returns the next question.
     */
    fun getNextQuestion(): Question? {
        return if (nextQuestionIndex < allQuestions.size) {
            val questionEntity = allQuestions[nextQuestionIndex]
            nextQuestionIndex++
            toQuestion(questionEntity)
        } else {
            null // No more questions
        }
    }


    /**
     * Invoked to update the DB with an answered question.
     */
    suspend fun updateQuestion(question: Question) = withContext(Dispatchers.IO) {
        val questionEntity = allQuestions.find { it.id == question.id }

        questionEntity?.let {
            Log.d(TAG, "update the DB with answered question!")

            it.chosenAnswer = question.chosenAnswer
            questionDao.insertQuestion(it)
        }
    }


    companion object {


        /**
         * For logging.
         */
        const val TAG = "QuizRepository"


    }

}