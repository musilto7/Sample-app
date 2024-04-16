package cz.musilto5.myflickerapp.domain.core

import org.junit.Test

class ResultExtensionsKtTest {

    @Test
    fun `when onSuccess is called on Success, then action is executed`() {
        var data: String? = null
        val expectedData = "data"
        val result: Result<String> = Result.Success(expectedData)
        result.onSuccess { data = it }
        assert(data == expectedData) { "Expected that onSuccess action should be executed" }
    }

    @Test
    fun `when onSuccess is called on Error, then action is not executed`() {
        var data: String? = null
        val result: Result<String> = Result.Error(ErrorType.Unknown)
        result.onSuccess { data = it }
        assert(data == null) { "Expected that onSuccess action should not be executed" }
    }

    @Test
    fun `when onError is called on Error, then action is executed`() {
        var errorType: ErrorType? = null
        var exception: Throwable? = null

        val expectedException = IllegalStateException("error")
        val result: Result<String> = Result.Error(ErrorType.Unknown, expectedException)
        result.onError { error, t ->
            errorType = error
            exception = t
        }
        assert(errorType == ErrorType.Unknown) { "Expected that onError action should be executed" }
        assert(exception == expectedException) { "Expected that onError action should be executed" }
    }

    @Test
    fun `when onError is called on Success, then action is not executed`() {
        var errorType: ErrorType? = null
        var exception: Throwable? = null

        val result: Result<String> = Result.Success("data")
        result.onError { error, t ->
            errorType = error
            exception = t
        }
        assert(errorType == null) { "Expected that onError action should not be executed" }
        assert(exception == null) { "Expected that onError action should not be executed" }
    }

    @Test
    fun `when map is called on Success, then return Success with mapped data`() {
        val result: Result<String> = Result.Success("data")
        val mappedResult = result.map { it.length }
        assert(mappedResult is Result.Success) { "Expected that map should return Success" }
        assert((mappedResult as Result.Success).data == 4) { "Expected that map should return Success with mapped data" }
    }

    @Test
    fun `when map is called on Error, then return Error`() {
        val result: Result<String> = Result.Error(ErrorType.Unknown)
        val mappedResult = result.map { it.length }
        assert(mappedResult is Result.Error) { "Expected that map should return Error" }
    }

    @Test
    fun `when mapError is called on Success, then return Success`() {
        val result: Result<String> = Result.Success("data")
        val mappedResult = result.mapError { ErrorType.Network }
        assert(mappedResult is Result.Success) { "Expected that mapError should return Success" }
    }

    @Test
    fun `when mapError is called on Error, then return Error with mapped error`() {
        val result: Result<String> = Result.Error(ErrorType.Unknown)
        val mappedResult = result.mapError { ErrorType.Network }
        assert(mappedResult is Result.Error) { "Expected that mapError should return Error" }
        assert((mappedResult as Result.Error).errorType == ErrorType.Network) { "Expected that mapError should return Error with mapped error" }
    }

    @Test
    fun `when chain is called on Success, then return transformed Result`() {
        val result: Result<String> = Result.Success("data")
        val transformedResult = result.chain { Result.Success(it.length) }
        assert(transformedResult is Result.Success) { "Expected that chain should return Success" }
        assert((transformedResult as Result.Success).data == 4) { "Expected that chain should return Success with transformed data" }
    }

    @Test
    fun `when chain is called on Error, then return Error`() {
        val result: Result<String> = Result.Error(ErrorType.Unknown)
        val transformedResult = result.chain { Result.Success(it.length) }
        assert(transformedResult is Result.Error) { "Expected that chain should return Error" }
    }
}