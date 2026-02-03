package cz.musilto5.myflickerapp.domain.core

import org.junit.Test

class ResultTest {

    @Test
    fun `when Success is created, then it holds data and is Success type`() {
        val result: Result<String, Error> = Result.Success("data")
        assert(result is Result.Success) { "Expected result to be Success" }
        assert((result as Result.Success).data == "data") { "Expected Success to hold correct data" }
    }

    @Test
    fun `when Error is created, then it holds error and is Error type`() {
        val result: Result<String, Error> = Result.Error(Error.Unknown)
        assert(result is Result.Error) { "Expected result to be Error" }
        assert((result as Result.Error).error == Error.Unknown) { "Expected Error to hold correct error" }
    }
}
