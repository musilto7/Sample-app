package cz.musilto5.myflickerapp.domain.core

sealed interface Error {
    data object Unknown : Error
    data object Network : Error
    data class NetworkBadState(val status: Int) : Error
}
