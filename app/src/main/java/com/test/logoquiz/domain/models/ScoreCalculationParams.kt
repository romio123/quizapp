package com.test.logoquiz.domain.models

data class ScoreCalculationParams(
    val questionStartTime: Long,
    val questionAnswerTime: Long?,
    val userAnswer: String?,
    val answer: String?
)