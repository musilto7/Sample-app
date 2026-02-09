package cz.musilto5.myflickerapp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import cz.musilto5.myflickerapp.presentation.feature.image.detail.view.ImageDetailScreen
import cz.musilto5.myflickerapp.presentation.feature.image.list.view.ImagesScreen

@Composable
fun FlickerAppNavigation() {
    val backStack = rememberNavBackStack(
        configuration = createNavConfiguration(),
        ImageListKey
    )

    NavDisplay(
        backStack = backStack,
        onBack = { backStack.navigateBack() },
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
                metadata = NavigationTransitions.detailScreenTransitions()
            ) { key ->
                ImageDetailScreen(
                    imageVO = key.imageVO,
                    onBackPressed = { backStack.navigateBack() }
                )
            }
        }
    )
}

private fun NavBackStack<NavKey>.navigateBack() {
    if (size > 1) {
        removeAt(lastIndex)
    }
}
