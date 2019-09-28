package com.test.logoquiz.domain.usecases.base

import com.test.logoquiz.utils.exceptions.Failure
import com.test.logoquiz.utils.functional.Either
import com.test.logoquiz.utils.helpers.ConcurrencyType
import com.test.logoquiz.utils.helpers.ControlledRunner
import com.test.logoquiz.utils.helpers.SingleRunner
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

abstract class OneShotRequestUseCase<Type> {

    private val singleRunner = SingleRunner()
    private val controlledRunner =
        ControlledRunner<Either<Type, Failure>>()

    protected abstract suspend fun run(): Either<Type, Failure>

    operator fun invoke(scope: CoroutineScope,
                        concurrencyType: ConcurrencyType = ConcurrencyType.JOIN_PREVIOUS,
                        onResult: (Either<Type, Failure>) -> Unit = {}) {
        val job = scope.async {
            when(concurrencyType) {
                ConcurrencyType.JOIN_PREVIOUS -> {
                    controlledRunner.joinPreviousOrRun{
                        run()
                    }
                }
                ConcurrencyType.AFTER_PREVIOUS -> {
                    singleRunner.afterPrevious {
                        run()
                    }
                }
                ConcurrencyType.CANCEL_PREVIOUS -> {
                    controlledRunner.cancelPreviousThenRun {
                        run()
                    }
                }
            }
        }
        scope.launch { onResult(job.await()) }
    }
}