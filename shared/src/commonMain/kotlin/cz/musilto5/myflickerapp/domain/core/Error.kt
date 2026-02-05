package cz.musilto5.myflickerapp.domain.core

interface Error {
    data object Unknown : Error
    data object Network : Error
    data class NetworkBadState(val status: Int) : Error
}
