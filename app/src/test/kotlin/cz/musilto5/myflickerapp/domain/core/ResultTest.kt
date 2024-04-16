package cz.musilto5.myflickerapp.domain.core

import cz.musilto5.myflickerapp.domain.core.Result
import org.junit.Test


class ResultTest {

    @Test
    fun `when isSuccess is called on Success, then return true`() {
        val result: Result<String> = Result.Success("data")
        assert(result.isSuccess()) { "Expected isSuccess to return true" }
    }

    @Test
    fun `when isSuccess is called on Error, then return false`() {
        val result: Result<String> = Result.Error(ErrorType.Unknown)
        assert(!result.isSuccess()) { "Expected isSuccess to return false" }
    }
}