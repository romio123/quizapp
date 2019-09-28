package com.test.logoquiz.domain.models

import com.google.gson.annotations.SerializedName

data class QuizQuestion(
    @SerializedName("imgUrl")
    val imgUrl: String?,
    @SerializedName("name")
    val name: String?
)