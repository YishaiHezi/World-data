package com.example.worlddata.quiz

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import data.quiz.Question
import data.quiz.QuizRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


/**
 * The view model needed for the quiz screen.
 */
@HiltViewModel
class QuizViewModel @Inject constructor(
    private val quizRepository: QuizRepository
) : ViewModel() {


    init {
        viewModelScope.launch {
            quizRepository.loadAllQuestions()
            onNextQuestion()
        }
    }


    /**
     * Mutable ui state for the quiz screen.
     */
    private val mutableUiState: MutableStateFlow<UiState> = MutableStateFlow(Loading)


    /**
     * Immutable ui state for the quiz screen.
     */
    val uiState: StateFlow<UiState> = mutableUiState


    /**
     * The number of correct answers the user answered.
     */
    private var correctAnswers = 0


    /**
     * The total number of questions the user answered.
     */
    private var totalQuestions = 0


    /**
     * Invoked when the user clicks on answer.
     */
    fun onAnswerClicked(answer: String) {
        val currentState = mutableUiState.value

        if (currentState is QuestionState) {
            val question = currentState.question
            val answeredQuestion = Question(question.id, question.imageRes, question.questionText, question.options, question.correctAnswer, answer)
            mutableUiState.value = QuestionState(answeredQuestion)

            totalQuestions++
            if (answer == question.correctAnswer)
                correctAnswers++

            viewModelScope.launch {
                quizRepository.updateQuestion(answeredQuestion)
            }
        }
    }


    /**
     * Gets the next question for the quiz.
     */
    fun onNextQuestion(){
        val nextQuestion = quizRepository.getNextQuestion()
        mutableUiState.value = nextQuestion?.let {
            QuestionState(nextQuestion)
        } ?:
        Finished(correctAnswers, totalQuestions)
    }


}


/**
 * The ui states.
 */
sealed interface UiState


/**
 * A default empty loading state.
 */
data object Loading : UiState


/**
 * Holds the data needed for a single question in the quiz.
 */
data class QuestionState(val question: Question) : UiState


/**
 * Shows a finish screen, after the user answered all the questions correctly.
 */
data class Finished(val correctAnswers: Int, val totalQuestions: Int) : UiState