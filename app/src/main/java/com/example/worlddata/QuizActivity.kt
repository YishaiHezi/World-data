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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.worlddata.ui.theme.AppTheme
import androidx.lifecycle.viewmodel.compose.viewModel


/**
 * The main screen that displays a question with multiple choices to answer.
 */
class QuizActivity : AppCompatActivity() {


    @Preview(showBackground = true)
    @Composable
    fun PreviewMyScreen() {
        AppTheme {
            QuizScreen()
        }
    }


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
    fun QuizScreen(viewModel: QuizViewModel = viewModel()) {
        val state by viewModel.uiState.collectAsState()
        QuizScreen(state)
    }


    /**
     * Shows a screen with image, question, and answers.
     */
    @Composable
    fun QuizScreen(state: UiState) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 16.dp, end = 16.dp, top = 64.dp, bottom = 32.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = state.imageRes),
                contentDescription = "Image for the question",
                modifier = Modifier.shadow(8.dp)
            )

            Text(
                text = state.question,
                style = MaterialTheme.typography.headlineLarge,
                color = MaterialTheme.colorScheme.onSurface,
                textAlign = TextAlign.Center
            )

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                state.answers.chunked(2).forEach { rowAnswers ->
                    if (rowAnswers.size == 2)
                        TwoChoices(rowAnswers[0], rowAnswers[1])
                    else
                        SingleChoice(rowAnswers[0])
                }
            }

        }
    }


    /**
     * Shows 2 answers next to each other.
     */
    @Composable
    fun TwoChoices(answer1: String, answer2: String) {
        Row {
            Button(
                onClick = { /* TODO */ },
                modifier = Modifier
                    .weight(1f)
                    .aspectRatio(1f)
                    .padding(8.dp),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text(
                    text = answer1,
                    style = MaterialTheme.typography.titleLarge,
                    textAlign = TextAlign.Center
                )
            }
            Button(
                onClick = { /* TODO */ },
                modifier = Modifier
                    .weight(1f)
                    .aspectRatio(1f)
                    .padding(8.dp),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text(
                    text = answer2,
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
    fun SingleChoice(answer: String) {
        Row {
            Spacer(modifier = Modifier
                .weight(0.5f)
                .aspectRatio(1f)
                .padding(8.dp))

            Button(
                onClick = { /* TODO */ },
                modifier = Modifier
                    .weight(1f)
                    .aspectRatio(1f)
                    .padding(8.dp),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text(
                    text = answer,
                    style = MaterialTheme.typography.titleLarge
                )
            }

            Spacer(modifier = Modifier
                .weight(0.5f)
                .aspectRatio(1f)
                .padding(8.dp))
        }
    }


}