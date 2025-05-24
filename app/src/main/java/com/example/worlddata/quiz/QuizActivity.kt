package com.example.worlddata.quiz

import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.worlddata.R
import com.example.worlddata.ShowSideConfetti
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
                this,
                state,
                onAnswerClick = ::onAnswerClicked,
                onNextClick = ::onNextClicked
            )

            is Finished -> FinishedScreen(state)
        }
    }


    /**
     * Shows a loading screen.
     */
    @Composable
    fun LoadingScreen() {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
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


    /**
     * Finish screen - after the user answered all the questions.
     */
    @Composable
    private fun FinishedScreen(state: Finished) {
        val title = if (state.totalQuestions == 0)
            getString(R.string.no_more_questions_title)
        else
            getString(R.string.finished_questions_title)

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 64.dp, end = 64.dp, top = 128.dp, bottom = 16.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    title,
                    style = MaterialTheme.typography.headlineLarge,
                    color = MaterialTheme.colorScheme.secondary,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold
                )

                if (state.totalQuestions != 0) {
                    Text(
                        getString(
                            R.string.finished_questions_subtitle,
                            state.correctAnswers,
                            state.totalQuestions
                        ),
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
        }

        if (state.totalQuestions != 0)
            ShowSideConfetti()
    }


    companion object {


        /**
         * A tag for log.
         */
        const val TAG = "QuizActivity"


    }


}

