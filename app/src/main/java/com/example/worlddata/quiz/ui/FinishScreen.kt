package com.example.worlddata.quiz.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.worlddata.R
import com.example.worlddata.ShowSideConfetti


/**
 * Finish screen - after the user answered all the questions.
 */
@Composable
fun FinishedScreen(state: Finished) {
    val context = LocalContext.current

    val title = if (state.totalQuestions == 0)
        context.getString(R.string.no_more_questions_title)
    else
        context.getString(R.string.finished_questions_title)

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
                    context.getString(
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