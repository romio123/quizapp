package com.test.logoquiz.data

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.test.logoquiz.domain.QuizRepo
import com.test.logoquiz.domain.models.QuizQuestion
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.InputStreamReader
import javax.inject.Inject

class QuizRepoImpl @Inject constructor(private val gson: Gson,
                                       private val context: Context): QuizRepo {
    override suspend fun fetchQuizQuestions(): List<QuizQuestion> {
        return withContext(Dispatchers.IO) {
            val quizQuestionInputStream = context.assets.open("raw/quiz_questions.json")
            gson.fromJson<List<QuizQuestion>>(InputStreamReader(quizQuestionInputStream),
                object: TypeToken<ArrayList<QuizQuestion>>(){}.type)
        }
    }
}