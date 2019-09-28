package com.test.logoquiz.ui.activities

import com.bumptech.glide.Glide
import com.test.logoquiz.R
import com.test.logoquiz.ui.base.BaseViewModelActivity
import com.test.logoquiz.ui.viewmodels.QuizViewModel
import com.test.logoquiz.utils.extensions.empty
import com.test.logoquiz.utils.extensions.observe
import com.test.logoquiz.utils.extensions.throttleClick
import kotlinx.android.synthetic.main.activity_quiz.*

class QuizActivity : BaseViewModelActivity<QuizViewModel>(QuizViewModel::class) {

    override fun getLayoutId(): Int = R.layout.activity_quiz


    override fun observeViewModel() {
        super.observeViewModel()
        viewModel.apply {
            observe(currQuestion) {
                it?.let {
                    loadImage(it.imgUrl ?: String.empty())
                }
            }

            observe(timer) {
                it?.let {
                    tv_timer.text = "Remaining: ${it} secs"
                }
            }

            observe(score) {
                it?.let {
                    tv_score.text = "Score: $it"
                }
            }
        }
    }

    private fun loadImage(url: String) {
        Glide.with(this)
            .load(url)
            .into(iv_quiz_question)
    }

    override fun initListeners() {
        super.initListeners()
        btn_submit.throttleClick {
            viewModel.onQuestionAnswered(et_answer.text.toString())
        }
    }
}
