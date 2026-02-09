package cz.musilto5.myflickerapp.presentation.core.error

import cz.musilto5.myflickerapp.domain.core.Error
import cz.musilto5.myflickerapp.generated.resources.Res
import cz.musilto5.myflickerapp.generated.resources.error_network
import cz.musilto5.myflickerapp.generated.resources.error_server
import cz.musilto5.myflickerapp.generated.resources.error_unknown
import org.jetbrains.compose.resources.StringResource

object ErrorMapper {

    fun mapToResource(error: Error): StringResource = when (error) {
        is Error.Network -> Res.string.error_network
        is Error.NetworkBadState -> Res.string.error_server
        is Error.Unknown -> Res.string.error_unknown
    }
}
