package com.example.worlddata

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.worlddata.ui.theme.AppTheme
import com.example.worlddata.ui.theme.correctGreen
import com.example.worlddata.ui.theme.wrongRed


/**
 * The main screen that displays a question with multiple choices to answer.
 */
class QuizActivity : AppCompatActivity() {


//    @Preview(showBackground = true)
//    @Composable
//    fun PreviewMyScreen() {
//        AppTheme {
//            QuizScreen()
//        }
//    }


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
        QuizScreen(uiState)

        if (uiState.chosenAnswer == uiState.correctAnswer)
            ShowCenteredConfetti()
    }


    /**
     * Shows a screen with image, question, and answers.
     */
    @Composable
    private fun QuizScreen(uiState: UiState) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 16.dp, end = 16.dp, top = 64.dp, bottom = 32.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = uiState.imageRes),
                contentDescription = "Image for the question",
                modifier = Modifier.shadow(8.dp)
            )

            Text(
                text = uiState.question,
                style = MaterialTheme.typography.headlineLarge,
                color = MaterialTheme.colorScheme.onSurface,
                textAlign = TextAlign.Center
            )

            if (uiState.chosenAnswer != null)
                ShowMessage(uiState)

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                uiState.answers.chunked(2).forEach { rowAnswers ->
                    if (rowAnswers.size == 2)
                        TwoChoices(rowAnswers[0], rowAnswers[1], uiState)
                    else
                        SingleChoice(rowAnswers[0], uiState)
                }
            }

        }
    }


    /**
     * Shows 2 answers next to each other.
     */
    @Composable
    private fun TwoChoices(
        choice1: String,
        choice2: String,
        uiState: UiState
    ) {
        Row {
            Button(
                onClick = { onAnswerClicked(uiState, choice1) },
                modifier = Modifier
                    .weight(1f)
                    .aspectRatio(1f)
                    .padding(8.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = buttonColor(choice1, uiState))
            ) {
                Text(
                    text = choice1,
                    style = MaterialTheme.typography.titleLarge,
                    textAlign = TextAlign.Center
                )
            }
            Button(
                onClick = { onAnswerClicked(uiState, choice2) },
                modifier = Modifier
                    .weight(1f)
                    .aspectRatio(1f)
                    .padding(8.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = buttonColor(choice2, uiState))
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
    private fun SingleChoice(choice: String, uiState: UiState) {
        Row {
            Spacer(
                modifier = Modifier
                    .weight(0.5f)
                    .aspectRatio(1f)
                    .padding(8.dp)
            )

            Button(
                onClick = { onAnswerClicked(uiState, choice) },
                modifier = Modifier
                    .weight(1f)
                    .aspectRatio(1f)
                    .padding(8.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = buttonColor(choice, uiState))
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
    private fun buttonColor(buttonText: String, uiState: UiState): Color {
        if (uiState.chosenAnswer == null)
            return MaterialTheme.colorScheme.primary

        if (buttonText == uiState.correctAnswer)
            return MaterialTheme.colorScheme.correctGreen

        if (buttonText == uiState.chosenAnswer)
            return MaterialTheme.colorScheme.wrongRed

        return MaterialTheme.colorScheme.primary
    }


    /**
     * Shows a "Correct!" / "Wrong..." message to the user based on its answer.
     */
    @Composable
    private fun ShowMessage(uiState: UiState) {
        val isUserCorrect = uiState.chosenAnswer == uiState.correctAnswer
        val text = if (isUserCorrect) "Correct!" else "Wrong!"
        val color = if (isUserCorrect) MaterialTheme.colorScheme.correctGreen else MaterialTheme.colorScheme.wrongRed

        Text(
            text = text,
            fontSize = 64.sp,
            style = MaterialTheme.typography.headlineLarge,
            color = color,
            fontWeight = FontWeight.Bold
        )
    }


    /**
     * Invoked when the user clicks on answer.
     */
    private fun onAnswerClicked(uiState: UiState, answer: String) {
        if (uiState.chosenAnswer == null)
            viewModel.onUserClickAnswer(answer)
    }


}

