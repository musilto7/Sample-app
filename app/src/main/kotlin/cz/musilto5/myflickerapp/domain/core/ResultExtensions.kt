package cz.musilto5.myflickerapp.domain.core

inline fun <DATA, ERROR : Error> Result<DATA, ERROR>.onSuccess(
    action: (DATA) -> Unit
): Result<DATA, ERROR> {
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

inline fun <DATA, ERROR : Error> Result<DATA, ERROR>.onError(
    action: (Error, Throwable?) -> Unit
): Result<DATA, ERROR> {
    when (this) {
        is Result.Success -> {
            // no-op
        }

        is Result.Error -> {
            action(this.error, this.exception)
        }
    }
    return this
}

inline fun <DATA1, DATA2, ERROR : Error> Result<DATA1, ERROR>.chain(
    transform: (DATA1) -> Result<DATA2, ERROR>
): Result<DATA2, ERROR> {
    return when (this) {
        is Result.Success -> {
            transform(this.data)
        }

        is Result.Error -> {
            Result.Error(this.error, this.exception)
        }
    }
}

inline fun <DATA1, DATA2, ERROR : Error> Result<DATA1, ERROR>.map(
    transform: (DATA1) -> DATA2
): Result<DATA2, ERROR> {
    return when (this) {
        is Result.Success -> {
            Result.Success(transform(this.data))
        }

        is Result.Error -> {
            Result.Error(this.error, this.exception)
        }
    }
}
