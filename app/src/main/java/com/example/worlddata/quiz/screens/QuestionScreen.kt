package com.example.worlddata.quiz.screens

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.worlddata.R
import com.example.worlddata.ShowCenteredConfetti
import com.example.worlddata.quiz.QuestionState
import com.example.worlddata.ui.theme.correctGreen
import com.example.worlddata.ui.theme.wrongRed
import data.quiz.Question


/**
 * Shows a screen with image, question, and answers.
 */
@Composable
fun QuestionScreen(
    context: Context,
    state: QuestionState,
    onAnswerClick: (question: Question, choice: String) -> Unit,
    onNextClick: () -> Unit
) {
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
                modifier = Modifier
                    .shadow(8.dp)
                    .sizeIn(maxHeight = 150.dp)
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

        Answers(question, onAnswerClick)

        question.chosenAnswer?.let {
            NextButton(context, onNextClick)
        }
    }

    if (question.chosenAnswer == question.correctAnswer)
        ShowCenteredConfetti()
}


/**
 * Shows a "Correct!" / "Wrong..." message to the user based on its answer.
 */
@Composable
private fun ShowMessage(question: Question) {
    val isUserCorrect = question.chosenAnswer == question.correctAnswer
    val text = if (isUserCorrect) "Correct!" else "Wrong!"
    val color =
        if (isUserCorrect) MaterialTheme.colorScheme.correctGreen else MaterialTheme.colorScheme.wrongRed

    Text(
        text = text,
        fontSize = 48.sp,
        style = MaterialTheme.typography.headlineLarge,
        color = color,
        fontWeight = FontWeight.Bold
    )
}


/**
 * Shows all the available choices (answers) to the question.
 */
@Composable
fun Answers(
    question: Question,
    onClick: (question: Question, choice: String) -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        question.options.chunked(2).forEach { rowAnswers ->
            Row {
                Answer(
                    modifier = Modifier
                        .weight(1f)
                        .aspectRatio(1f)
                        .padding(8.dp),
                    question,
                    rowAnswers[0]
                ) {
                    onClick(question, rowAnswers[0])
                }

                Answer(
                    modifier = Modifier
                        .weight(1f)
                        .aspectRatio(1f)
                        .padding(8.dp),
                    question,
                    rowAnswers[1]
                ) {
                    onClick(question, rowAnswers[1])
                }

            }
        }
    }
}


/**
 * A single answer to a question.
 */
@Composable
private fun Answer(
    modifier: Modifier = Modifier,
    question: Question,
    choice: String,
    onClick: () -> Unit
) {
    Button(
        onClick = { onClick() },
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = buttonColor(
                choice,
                question
            )
        )
    ) {
        Text(
            text = choice,
            style = MaterialTheme.typography.titleLarge,
            textAlign = TextAlign.Center
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
 * Creates a button that calls for the next question to show.
 */
@Composable
fun NextButton(context: Context, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        Modifier.fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.correctGreen)
    ) {
        Text(
            text = context.getString(R.string.next_question),
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowForward,
            contentDescription = context.getString(R.string.next_question),
            modifier = Modifier.size(ButtonDefaults.IconSize) // optional size
        )
    }
}