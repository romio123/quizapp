package com.test.logoquiz.utils.exceptions

import android.os.RemoteException

/**
 * Base Class for handling errors/failures/exceptions.
 * Every feature specific failure should extend [FeatureFailure] class.
 */
sealed class Failure(open val title: String?, open val msg: String?, open val reason: Throwable?): Throwable(msg, reason) {

    object InsufficientParam: Failure("Insufficient Param")

    object InvalidResult: Failure("Invalid Result", "Got a null response")

    class RemoteFailure(title: String, msg: String, ex: RemoteException): Failure(title, msg, ex) {
        constructor(title: String, ex: RemoteException): this(title, ex.message ?: "", ex)
        constructor(ex: RemoteException): this("Something went wrong!", ex.message ?: "", ex)
    }

    constructor(title: String?) : this(title, "Something went wrong!", null)

    constructor(cause: Throwable) : this("Something went wrong!", cause.toString(), cause)

    constructor(title: String?, msg: String?) : this(title, msg, null)

    constructor() : this("Something went wrong!", "Please stay with us, while we fix it.", null)

    object NetworkConnection : Failure("Something went wrong!", "Please check your Internet Connection")

    /** * Extend this class for feature specific failures.*/
    abstract class FeatureFailure: Failure()
}