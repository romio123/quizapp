package com.test.logoquiz.utils.functional


/**
 * Represents a value of one of two possible types (a disjoint union).
 * Instances of [Either] are either an instance of [Left] or [Right].
 * FP Convention dictates that [Left] is used for "failure"
 * and [Right] is used for "success".
 *
 * @see Left
 * @see Right
 */
sealed class Either<out L, out R> {
    /** * Represents the left side of [Either] class which by convention is a "Failure". */
    data class Left<out L>(val a: L) : Either<L, Nothing>()
    /** * Represents the right side of [Either] class which by convention is a "Success". */
    data class Right<out R>(val b: R) : Either<Nothing, R>()

    val isRight get() = this is Right<R>
    val isLeft get() = this is Left<L>

    fun <L> left(a: L) = Left(a)
    fun <R> right(b: R) = Right(b)

    fun either(fnL: (L) -> Unit, fnR: (R) -> Unit): Any =
        when (this) {
            is Left -> fnL(a)
            is Right -> fnR(b)
        }
}

fun <A, B, C> ((A) -> B).c(f: (B) -> C): (A) -> C = {
    f(this(it))
}

fun <T, L, R> Either<L, R>.flatMap(fn: (L) -> Either<T, R>): Either<T, R> =
    when (this) {
        is Either.Left -> fn(a)
        is Either.Right -> Either.Right(b)
    }

fun <T, L, R> Either<L, R>.map(fn: (L) -> (T)): Either<T, R> = this.flatMap(fn.c(::left))