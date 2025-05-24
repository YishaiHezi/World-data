package com.example.worlddata.quiz

import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import com.example.worlddata.quiz.screens.FinishedScreen
import com.example.worlddata.ui.components.LoadingScreen
import com.example.worlddata.quiz.screens.QuestionScreen
import com.example.worlddata.ui.theme.AppTheme
import dagger.hilt.android.AndroidEntryPoint
import data.quiz.Question


/**
 * The main screen that displays a question with multiple choices to answer.
 */
@AndroidEntryPoint
class QuizActivity : AppCompatActivity() {


    @Preview(showBackground = true)
    @Composable
    fun PreviewMyScreen() {
        AppTheme {
            FinishedScreen(Finished(20, 30))
        }
    }


    /**
     * The view model for this activity.
     */
    private val viewModel: QuizViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            AppTheme {
                QuizScreen()
            }
        }
    }


    /**
     * The screen for this activity.
     */
    @Composable
    private fun QuizScreen() {
        val uiState by viewModel.uiState.collectAsState()

        when (val state = uiState) {
            is Loading -> LoadingScreen()
            is QuestionState -> QuestionScreen(
                state,
                onAnswerClick = ::onAnswerClicked,
                onNextClick = ::onNextClicked
            )
            is Finished -> FinishedScreen(state)
        }
    }


    /**
     * Invoked when the user clicks on answer.
     */
    private fun onAnswerClicked(question: Question, answer: String) {
        Log.d(TAG, "User clicked on answer: $answer, in the question: $question")

        if (question.chosenAnswer == null)
            viewModel.onAnswerClicked(answer)
    }


    /**
     * Invoked when the user clicks on "next question".
     */
    private fun onNextClicked(){
        Log.d(TAG, "Next question clicked!")

        viewModel.onNextQuestion()
    }


    companion object {


        /**
         * A tag for log.
         */
        const val TAG = "QuizActivity"


    }


}

