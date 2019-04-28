package com.danieh.kotlinmvi.core.exception

/**
 * Created by danieh on 19/04/2019.
 */
sealed class Failure {

    class NetworkConnection : BaseFailure()
    class ServerError(val throwable: Throwable?) : BaseFailure()

    class ReadJson : BaseFailure()

    // Extend this for specific exceptions
    abstract class BaseFailure : Failure()
}