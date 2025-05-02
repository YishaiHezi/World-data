package com.example.worlddata.quiz

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.worlddata.R
import com.example.worlddata.ShowCenteredConfetti
import com.example.worlddata.ShowSideConfetti
import com.example.worlddata.ui.theme.AppTheme
import com.example.worlddata.ui.theme.correctGreen
import com.example.worlddata.ui.theme.wrongRed
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
            FinishedScreen(Finished(20,30))
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
            is QuestionState -> QuestionScreen(state)
            is Finished -> FinishedScreen(state)
        }
    }


    /**
     * Shows a screen with image, question, and answers.
     */
    @Composable
    private fun QuestionScreen(state: QuestionState) {
        val question = state.question

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            question.imageRes?.let {
                Image(
                    painter = painterResource(id = it),
                    contentDescription = "Image for the question",
                    modifier = Modifier.shadow(8.dp).sizeIn(maxHeight = 150.dp)
                )
            }

            Text(
                text = question.questionText,
                style = MaterialTheme.typography.headlineLarge,
                color = MaterialTheme.colorScheme.onSurface,
                textAlign = TextAlign.Center
            )

            question.chosenAnswer?.let {
                ShowMessage(question)
            }

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                question.options.chunked(2).forEach { rowAnswers ->
                    if (rowAnswers.size == 2)
                        TwoChoices(rowAnswers[0], rowAnswers[1], question)
                    else
                        SingleChoice(rowAnswers[0], question)
                }
            }

            question.chosenAnswer?.let {
                NextButton()
            }
        }

        if (question.chosenAnswer == question.correctAnswer)
            ShowCenteredConfetti()
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
     * Shows 2 answers next to each other.
     */
    @Composable
    private fun TwoChoices(
        choice1: String,
        choice2: String,
        question: Question
    ) {
        Row {
            Button(
                onClick = { onAnswerClicked(question, choice1) },
                modifier = Modifier
                    .weight(1f)
                    .aspectRatio(1f)
                    .padding(8.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = buttonColor(choice1, question))
            ) {
                Text(
                    text = choice1,
                    style = MaterialTheme.typography.titleLarge,
                    textAlign = TextAlign.Center
                )
            }
            Button(
                onClick = { onAnswerClicked(question, choice2) },
                modifier = Modifier
                    .weight(1f)
                    .aspectRatio(1f)
                    .padding(8.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = buttonColor(choice2, question))
            ) {
                Text(
                    text = choice2,
                    style = MaterialTheme.typography.titleLarge,
                    textAlign = TextAlign.Center
                )
            }
        }
    }


    /**
     * Shows a single answer.
     */
    @Composable
    private fun SingleChoice(choice: String, question: Question) {
        Row {
            Spacer(
                modifier = Modifier
                    .weight(0.5f)
                    .aspectRatio(1f)
                    .padding(8.dp)
            )

            Button(
                onClick = { onAnswerClicked(question, choice) },
                modifier = Modifier
                    .weight(1f)
                    .aspectRatio(1f)
                    .padding(8.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = buttonColor(choice, question))
            ) {
                Text(
                    text = choice,
                    style = MaterialTheme.typography.titleLarge
                )
            }

            Spacer(
                modifier = Modifier
                    .weight(0.5f)
                    .aspectRatio(1f)
                    .padding(8.dp)
            )
        }
    }


    /**
     * Returns the required background color for a button based on its text and the ui state.
     */
    @Composable
    private fun buttonColor(buttonText: String, question: Question): Color {
        if (question.chosenAnswer == null)
            return MaterialTheme.colorScheme.primary

        if (buttonText == question.correctAnswer)
            return MaterialTheme.colorScheme.correctGreen

        if (buttonText == question.chosenAnswer)
            return MaterialTheme.colorScheme.wrongRed

        return MaterialTheme.colorScheme.primary
    }


    /**
     * Shows a "Correct!" / "Wrong..." message to the user based on its answer.
     */
    @Composable
    private fun ShowMessage(question: Question) {
        val isUserCorrect = question.chosenAnswer == question.correctAnswer
        val text = if (isUserCorrect) "Correct!" else "Wrong!"
        val color = if (isUserCorrect) MaterialTheme.colorScheme.correctGreen else MaterialTheme.colorScheme.wrongRed

        Text(
            text = text,
            fontSize = 48.sp,
            style = MaterialTheme.typography.headlineLarge,
            color = color,
            fontWeight = FontWeight.Bold
        )
    }


    /**
     * Invoked when the user clicks on answer.
     */
    private fun onAnswerClicked(question: Question, answer: String) {
        if (question.chosenAnswer == null)
            viewModel.onUserClickAnswer(answer)
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

                if (state.totalQuestions != 0){
                    Text(
                        getString(R.string.finished_questions_subtitle, state.correctAnswers, state.totalQuestions),
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
        }

        ShowSideConfetti()
    }


    /**
     * Creates a button that calls for the next question to show.
     */
    @Composable
    fun NextButton(){
        Button(
            onClick = { viewModel.onNextQuestion() },
            Modifier.fillMaxWidth(),
        ) {
            Text(
                text = "Next Question", // todo: update
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                contentDescription = "Next Question", // todo: update
                modifier = Modifier.size(ButtonDefaults.IconSize) // optional size
            )
        }
    }


}

