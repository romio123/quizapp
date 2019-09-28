package com.test.logoquiz.domain

import com.test.logoquiz.domain.models.QuizQuestion

interface QuizRepo {
    suspend fun fetchQuizQuestions(): List<QuizQuestion>
}