package cz.musilto5.myflickerapp.presentation.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import androidx.savedstate.serialization.SavedStateConfiguration
import cz.musilto5.myflickerapp.presentation.feature.image.detail.view.ImageDetailScreen
import cz.musilto5.myflickerapp.presentation.feature.image.list.view.ImagesScreen
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic

private const val TransitionDurationMillis = 300

@Composable
fun FlickerAppNavigation() {
    val configuration = SavedStateConfiguration {
        serializersModule = SerializersModule {
            polymorphic(NavKey::class) {
                subclass(ImageListKey::class, ImageListKey.serializer())
                subclass(ImageDetailKey::class, ImageDetailKey.serializer())
            }
        }
    }

    val backStack = rememberNavBackStack(
        configuration = configuration,
        ImageListKey
    )

    NavDisplay(
        backStack = backStack,
        onBack = {
            if (backStack.size > 1) {
                backStack.removeAt(backStack.lastIndex)
            }
        },
        entryDecorators = listOf(
            rememberSaveableStateHolderNavEntryDecorator(),
            rememberViewModelStoreNavEntryDecorator()
        ),
        entryProvider = entryProvider {
            entry<ImageListKey> {
                ImagesScreen(
                    navigateToImageDetail = { imageVO ->
                        backStack.add(ImageDetailKey(imageVO))
                    }
                )
            }
            entry<ImageDetailKey>(
                metadata = NavDisplay.transitionSpec {
                    slideInVertically(
                        initialOffsetY = { it },
                        animationSpec = tween(TransitionDurationMillis)
                    ) togetherWith ExitTransition.KeepUntilTransitionsFinished
                } + NavDisplay.popTransitionSpec {
                    EnterTransition.None togetherWith
                        slideOutVertically(
                            targetOffsetY = { it },
                            animationSpec = tween(TransitionDurationMillis)
                        )
                } + NavDisplay.predictivePopTransitionSpec {
                    EnterTransition.None togetherWith
                        slideOutVertically(
                            targetOffsetY = { it },
                            animationSpec = tween(TransitionDurationMillis)
                        )
                }
            ) { key ->
                ImageDetailScreen(
                    imageVO = key.imageVO,
                    onBackPressed = {
                        if (backStack.size > 1) {
                            backStack.removeAt(backStack.lastIndex)
                        }
                    }
                )
            }
        }
    )
}
