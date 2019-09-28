package com.test.logoquiz.domain.usecases

import com.test.logoquiz.domain.models.ScoreCalculationParams
import com.test.logoquiz.domain.usecases.base.OneShotRequestWithParamUseCase
import com.test.logoquiz.utils.exceptions.Failure
import com.test.logoquiz.utils.functional.Either
import javax.inject.Inject
import javax.inject.Named

class ScoreCalculationUseCase @Inject constructor(@Named("score_factor")
                                                  private val scorecalculationFactor: Int):
    OneShotRequestWithParamUseCase<ScoreCalculationParams, Long>() {
    override suspend fun run(param: ScoreCalculationParams): Either<Long, Failure> {
        return if(param.questionAnswerTime != null  && param.userAnswer != null) {
            Either.Left((param.questionAnswerTime - param.questionStartTime)
                .times(scorecalculationFactor))
        } else {
            Either.Right(Failure.InsufficientParam)
        }
    }
}