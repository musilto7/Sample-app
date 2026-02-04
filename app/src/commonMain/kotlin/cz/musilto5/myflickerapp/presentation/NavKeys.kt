package cz.musilto5.myflickerapp.presentation

import androidx.navigation3.runtime.NavKey
import cz.musilto5.myflickerapp.presentation.feature.image.model.FlickerImageVO
import kotlinx.serialization.Serializable

@Serializable
data object ImageListKey : NavKey

@Serializable
data class ImageDetailKey(val imageVO: FlickerImageVO) : NavKey
