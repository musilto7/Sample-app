package cz.musilto5.myflickerapp.presentation

import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import cz.musilto5.myflickerapp.presentation.core.NavUtils
import cz.musilto5.myflickerapp.presentation.feature.image.detail.view.ImageDetailScreen
import cz.musilto5.myflickerapp.presentation.feature.image.list.view.ImagesScreen
import cz.musilto5.myflickerapp.presentation.feature.image.model.FlickerImageVO

@Composable
fun FlickerAppNavigation() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = NAVIGATION_IMAGES_LIST
    ) {
        imageListComposable(navController)
        imageDetailComposable(navController)
    }
}


private fun NavGraphBuilder.imageListComposable(navController: NavHostController) {
    composable(route = NAVIGATION_IMAGES_LIST) {
        ImagesScreen(navigateToImageDetail = {
            navigateToImageDetail(navController, it)
        })
    }
}

private fun navigateToImageDetail(navController: NavController, imageVO: FlickerImageVO) {
    navController.navigate(
        NAVIGATION_IMAGE_DETAIL.replace(
            "{$NAVIGATION_PARAM_IMAGE_DETAIL}",
            NavUtils.toJsonString(imageVO)
        )
    )
}

private fun NavGraphBuilder.imageDetailComposable(navController: NavHostController) {
    composable(
        route = NAVIGATION_IMAGE_DETAIL,
        arguments = listOf(
            navArgument(NAVIGATION_PARAM_IMAGE_DETAIL) {
                type = NavType.StringType
            }
        ),
        enterTransition = {
            slideInVertically(initialOffsetY = { it })
        },
        exitTransition = {
            slideOutVertically(targetOffsetY = { it })
        }
    ) { navBackStackEntry ->
        ImageDetailScreen(navBackStackEntry, navController)
    }
}

@Composable
private fun ImageDetailScreen(
    navBackStackEntry: NavBackStackEntry,
    navController: NavHostController
) {
    val serializedImageVo =
        navBackStackEntry.arguments!!.getString(NAVIGATION_PARAM_IMAGE_DETAIL)!!
    ImageDetailScreen(NavUtils.fromJsonString(serializedImageVo),
        onBackPressed = {
            navController.popBackStack()
        })
}

const val NAVIGATION_IMAGES_LIST = "flickerImages"
const val NAVIGATION_PARAM_IMAGE_DETAIL = "imageDetail"
const val NAVIGATION_IMAGE_DETAIL =
    "flickerImages/detail?$NAVIGATION_PARAM_IMAGE_DETAIL={$NAVIGATION_PARAM_IMAGE_DETAIL}"
