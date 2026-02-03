package cz.musilto5.myflickerapp.domain.core

typealias ErrorType = Error

sealed class Result<out DATA, out ERROR : ErrorType> {

    data class Success<out DATA, out ERROR : ErrorType>(val data: DATA) : Result<DATA, ERROR>()

    data class Error<out DATA, out ERROR : ErrorType>(
        val error: ERROR,
        val exception: Throwable? = null,
    ) : Result<DATA, ERROR>()
}
