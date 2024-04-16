package cz.musilto5.myflickerapp.domain.core


inline fun <DATA> Result<DATA>.onSuccess(
    action: (DATA) -> Unit
): Result<DATA> {
    when (this) {
        is Result.Success -> {
            action(this.data)
        }

        is Result.Error -> {
            // no-op
        }
    }
    return this
}

inline fun <DATA> Result<DATA>.onError(
    action: (ErrorType, Throwable?) -> Unit
): Result<DATA> {
    when (this) {
        is Result.Success -> {
            // no-op
        }

        is Result.Error -> {
            action(this.errorType, this.exception)
        }
    }
    return this
}

inline fun <DATA1, DATA2> Result<DATA1>.chain(
    transform: (DATA1) -> Result<DATA2>
): Result<DATA2> {
    return when (this) {
        is Result.Success -> {
            transform(this.data)
        }

        is Result.Error -> {
            Result.Error(this.errorType, this.exception)
        }
    }
}

inline fun <DATA1, DATA2> Result<DATA1>.map(
    transform: (DATA1) -> DATA2
): Result<DATA2> {
    return when (this) {
        is Result.Success -> {
            Result.Success(transform(this.data))
        }

        is Result.Error -> {
            Result.Error(this.errorType, this.exception)
        }
    }
}


inline fun <DATA> Result<DATA>.mapError(
    transform: (ErrorType) -> ErrorType
): Result<DATA> {
    return when (this) {
        is Result.Success -> {
            Result.Success(this.data)
        }

        is Result.Error -> {
            Result.Error(transform(this.errorType), this.exception)
        }
    }
}
