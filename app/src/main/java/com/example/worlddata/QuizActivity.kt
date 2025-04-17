package com.example.worlddata

import android.os.Bundle
import androidx.activity.compose.setContent
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.worlddata.ui.theme.AppTheme
import androidx.lifecycle.viewmodel.compose.viewModel
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
    private fun QuizScreen(viewModel: QuizViewModel = viewModel()) {
        val uiState by viewModel.uiState.collectAsState()
        var userAnswer by remember { mutableStateOf(AnswerState.Unanswered) }

        LaunchedEffect(uiState.question) {
            userAnswer = AnswerState.Unanswered
        }

        val onAnswerClick: (String) -> Unit = { answer ->
            userAnswer = if (answer == uiState.correctAnswer)
                AnswerState.Correct
            else
                AnswerState.Wrong
        }

        QuizScreen(uiState, onAnswerClick, userAnswer)

        if (userAnswer == AnswerState.Correct)
            ShowCenteredConfetti()
    }


    /**
     * Shows a screen with image, question, and answers.
     */
    @Composable
    private fun QuizScreen(uiState: UiState, onAnswerClick: (String) -> Unit, userAnswer: AnswerState) {
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

            if (userAnswer != AnswerState.Unanswered)
                ShowMessage(userAnswer)

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                uiState.answers.chunked(2).forEach { rowAnswers ->
                    if (rowAnswers.size == 2)
                        TwoChoices(rowAnswers[0], rowAnswers[1], onAnswerClick)
                    else
                        SingleChoice(rowAnswers[0], onAnswerClick)
                }
            }

        }
    }


    /**
     * Shows 2 answers next to each other.
     */
    @Composable
    private fun TwoChoices(choice1: String, choice2: String, onAnswerClick: (String) -> Unit) {
        Row {
            Button(
                onClick = { onAnswerClick(choice1) },
                modifier = Modifier
                    .weight(1f)
                    .aspectRatio(1f)
                    .padding(8.dp),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text(
                    text = choice1,
                    style = MaterialTheme.typography.titleLarge,
                    textAlign = TextAlign.Center
                )
            }
            Button(
                onClick = { onAnswerClick(choice2) },
                modifier = Modifier
                    .weight(1f)
                    .aspectRatio(1f)
                    .padding(8.dp),
                shape = RoundedCornerShape(16.dp)
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
    private fun SingleChoice(choice: String, onAnswerClick: (String) -> Unit) {
        Row {
            Spacer(modifier = Modifier
                .weight(0.5f)
                .aspectRatio(1f)
                .padding(8.dp))

            Button(
                onClick = { onAnswerClick(choice) },
                modifier = Modifier
                    .weight(1f)
                    .aspectRatio(1f)
                    .padding(8.dp),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text(
                    text = choice,
                    style = MaterialTheme.typography.titleLarge
                )
            }

            Spacer(modifier = Modifier
                .weight(0.5f)
                .aspectRatio(1f)
                .padding(8.dp))
        }
    }


    /**
     * Shows a "Correct!" / "Wrong..." message to the user based on its answer.
     */
    @Composable
    private fun ShowMessage(userAnswer: AnswerState){
        val text = if (userAnswer == AnswerState.Correct) "Correct!" else "Wrong!"
        val color = if (userAnswer == AnswerState.Correct) MaterialTheme.colorScheme.correctGreen else MaterialTheme.colorScheme.wrongRed

        Text(
            text = text,
            fontSize = 64.sp,
            style = MaterialTheme.typography.headlineLarge,
            color = color,
            fontWeight = FontWeight.Bold
        )
    }

}

enum class AnswerState {
    Unanswered,
    Correct,
    Wrong
}

