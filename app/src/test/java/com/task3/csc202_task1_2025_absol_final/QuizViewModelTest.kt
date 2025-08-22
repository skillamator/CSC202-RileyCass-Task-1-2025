package com.task3.csc202_task1_2025_absol_final


import androidx.lifecycle.SavedStateHandle
import org.junit.Assert.assertEquals
import org.junit.Test

class QuizViewModelTest {
    @Test
    fun providesExpectedQuestionText(){
        val savedStateHandle = SavedStateHandle()
        val quizViewModel = QuizViewModel(savedStateHandle)
        assertEquals(R.string.question_australia, quizViewModel.currentQuestionText)
    }

    @Test
    fun wrapAroundQuestionBank(){
        val savedStateHandle = SavedStateHandle(mapOf(CURRENT_INDEX_KEY to 5))
        val quizViewModel = QuizViewModel(savedStateHandle)
        assertEquals(R.string.question_asia, quizViewModel.currentQuestionText)
        quizViewModel.moveToNext()
        assertEquals(R.string.question_australia, quizViewModel.currentQuestionText)
    }

    @Test
    fun providesExpectedQuestionAnswer(){
        val savedStateHandle = SavedStateHandle()
        val quizViewModel = QuizViewModel(savedStateHandle)
        assertEquals(true, quizViewModel.currentQuestionAnswer)
        quizViewModel.moveToNext()
        quizViewModel.moveToNext()
        assertEquals(false, quizViewModel.currentQuestionAnswer)
    }
}


