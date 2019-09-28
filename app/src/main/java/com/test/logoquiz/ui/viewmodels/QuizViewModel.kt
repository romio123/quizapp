package com.test.logoquiz.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.test.logoquiz.domain.models.QuizQuestion
import com.test.logoquiz.domain.models.ScoreCalculationParams
import com.test.logoquiz.domain.usecases.FetchQuizQuestionsUseCase
import com.test.logoquiz.domain.usecases.ScoreCalculationUseCase
import com.test.logoquiz.ui.base.BaseViewModel
import com.test.logoquiz.ui.models.RequestStatus
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class QuizViewModel @Inject constructor(
    private val fetchQuizQuestions: FetchQuizQuestionsUseCase,
    private val calculateScore: ScoreCalculationUseCase
):
    BaseViewModel() {

    private val quizQuestions = arrayListOf<QuizQuestion>()

    private val _fetchStatus = MutableLiveData<RequestStatus>()
    private val _currQuestion = MutableLiveData<QuizQuestion>()
    private val _timer = MutableLiveData<Long>()
    private val _score = MutableLiveData<Long>()

    val fetchStatus: LiveData<RequestStatus>
        get() = _fetchStatus
    val currQuestion: LiveData<QuizQuestion>
        get() = _currQuestion
    val timer: LiveData<Long>
        get() = _timer
    val score: LiveData<Long>
        get() = _score

    private var currQuestionIndex = 0

    private var timerDisposable: Disposable? = null
    private var nextQuestionTimerDisposable: Disposable? = null
    private var questionStartTime: Long = 0L


    init {
        fetchQuizQuestions()
    }


    private fun fetchQuizQuestions() {
        _fetchStatus.value = RequestStatus.LOADING
        fetchQuizQuestions(viewModelScope) {
            it.either({
                _fetchStatus.value = RequestStatus.SUCCESS
                quizQuestions.clear()
                quizQuestions.addAll(it)
                startGame()
            }, {
                Timber.e(it.reason)
                _fetchStatus.value = RequestStatus.FAILED
            })
        }
    }

    fun onQuestionAnswered(userAnswer: String) {
        calculateScore(viewModelScope, ScoreCalculationParams(questionStartTime,
            System.currentTimeMillis(), userAnswer, currQuestion.value?.name)){
            it.either({
                _score.value = _score.value?.plus(it)
                showQuestion()
            }, {
                Timber.e(it)
            })
        }
    }

    @Synchronized
    private fun startGame() {
        _score.value = 0L
        showQuestion()
    }

    @Synchronized
    private fun showQuestion() {
        nextQuestionTimerDisposable?.dispose()
        if(currQuestionIndex >= quizQuestions.size) {
            return
        }
        _currQuestion.postValue(quizQuestions[currQuestionIndex])
        questionStartTime = System.currentTimeMillis()
        startCountDownTimer()
        currQuestionIndex++
        nextQuestionTimerDisposable = Observable.timer(20, TimeUnit.SECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                showQuestion()
            }, {

            })
    }

    private fun startCountDownTimer() {
        timerDisposable?.dispose()
        var time = 0L
        timerDisposable = Observable.interval(1, TimeUnit.SECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                _timer.postValue(20 - time++)
            }, {

            })
    }
}