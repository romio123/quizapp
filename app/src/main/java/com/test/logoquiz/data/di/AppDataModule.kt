package com.test.logoquiz.data.di

import com.test.logoquiz.data.QuizRepoImpl
import com.test.logoquiz.domain.QuizRepo
import dagger.Binds
import dagger.Module

@Module
abstract class AppDataModule {
    @Binds
    abstract fun bindQuizRepo(quizRepoImpl: QuizRepoImpl): QuizRepo
}