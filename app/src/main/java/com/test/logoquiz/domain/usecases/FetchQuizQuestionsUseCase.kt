package com.test.logoquiz.domain.usecases

import com.google.gson.JsonSyntaxException
import com.test.logoquiz.domain.QuizRepo
import com.test.logoquiz.domain.models.QuizQuestion
import com.test.logoquiz.domain.usecases.base.OneShotRequestUseCase
import com.test.logoquiz.utils.exceptions.Failure
import com.test.logoquiz.utils.functional.Either
import javax.inject.Inject

class FetchQuizQuestionsUseCase @Inject constructor(private val quizRepo: QuizRepo):
    OneShotRequestUseCase<List<QuizQuestion>>() {
    override suspend fun run(): Either<List<QuizQuestion>, Failure> {
        return try {
            val quizQuestions = quizRepo.fetchQuizQuestions()
            Either.Left(quizQuestions)
        } catch (ex: JsonSyntaxException) {
            Either.Right(Failure.InvalidResult)
        }
    }
}