package com.example.worlddata

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow


/**
 * The view model needed for the quiz screen.
 */
class QuizViewModel : ViewModel() {


    /**
     * Mutable ui state for the quiz screen.
     */
    private val mutableUiState = MutableStateFlow(
        UiState(
            imageRes = R.drawable.ic_flag_ar,
            question = "This is a test question! what flag is this?",
            answers = listOf("Saudi Arabia", "Pakistan", "Turkey", "Argentina"),
            correctAnswer = "Argentina",
            chosenAnswer = null
        )
    )


    /**
     * Immutable ui state for the quiz screen.
     */
    val uiState: StateFlow<UiState> = mutableUiState


    /**
     * Invoked when the user clicks on answer.
     */
    fun onUserClickAnswer(answer: String) {
        val currentState = mutableUiState.value
        mutableUiState.value = UiState(
            currentState.imageRes,
            currentState.question,
            currentState.answers,
            currentState.correctAnswer,
            answer
        )
    }


}


/**
 * Holds the data needed for a single question in the quiz.
 */
data class UiState(
    val imageRes: Int,
    val question: String,
    val answers: List<String>,
    val correctAnswer: String,
    val chosenAnswer: String?
)