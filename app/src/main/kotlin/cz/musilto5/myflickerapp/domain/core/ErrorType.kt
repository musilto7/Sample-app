package cz.musilto5.myflickerapp.domain.core

sealed interface ErrorType {
    data object Unknown : ErrorType

    data object Network : ErrorType
}