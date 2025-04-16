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
            imageRes = R.drawable.ic_flag_af,
            question = "This is a test question! what flag is this?",
            answers = listOf("Saudi Arabia", "Pakistan", "Turkey", "Afghanistan"),
            correctAnswer = "Afghanistan"
        )
    )


    /**
     * Immutable ui state for the quiz screen.
     */
    val uiState: StateFlow<UiState> = mutableUiState


}


/**
 * Holds the data needed for a single question in the quiz.
 */
data class UiState(
    val imageRes: Int,
    val question: String,
    val answers: List<String>,
    val correctAnswer: String

)