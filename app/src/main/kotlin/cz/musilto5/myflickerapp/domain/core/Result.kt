package cz.musilto5.myflickerapp.domain.core

import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.contract

sealed class Result<DATA> {

    @OptIn(ExperimentalContracts::class)
    fun isSuccess(): Boolean {
        contract { returns(true) implies (this@Result is Success<DATA>) }
        return this is Success
    }

    @OptIn(ExperimentalContracts::class)
    fun isError(): Boolean {
        contract { returns(true) implies (this@Result is Error<DATA>) }
        return this is Success
    }

    data class Success<DATA>(val data: DATA) : Result<DATA>()
    data class Error<DATA>(
        val errorType: ErrorType,
        val exception: Throwable? = null,
    ) : Result<DATA>()
}