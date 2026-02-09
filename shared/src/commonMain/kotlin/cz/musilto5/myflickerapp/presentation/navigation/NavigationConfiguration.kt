package cz.musilto5.myflickerapp.presentation.navigation

import androidx.navigation3.runtime.NavKey
import androidx.savedstate.serialization.SavedStateConfiguration
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic

fun createNavConfiguration() = SavedStateConfiguration {
    serializersModule = SerializersModule {
        polymorphic(NavKey::class) {
            subclass(ImageListKey::class, ImageListKey.serializer())
            subclass(ImageDetailKey::class, ImageDetailKey.serializer())
        }
    }
}
