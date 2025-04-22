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
            val firstQuestion = quizRepository.getNextQuestion()
            firstQuestion?.let {
                mutableUiState.value = QuestionState(it)
            }
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
     * Invoked when the user clicks on answer.
     */
    fun onUserClickAnswer(answer: String) {
        val currentState = mutableUiState.value

        if (currentState is QuestionState) {
            val question = currentState.question
            val answeredQuestion = Question(
                question.id,
                question.imageRes,
                question.questionText,
                question.options,
                question.correctAnswer,
                answer
            )
            mutableUiState.value = QuestionState(answeredQuestion)

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
        mutableUiState.value = nextQuestion?.let { QuestionState(nextQuestion) } ?: Finished
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


data object Finished : UiState