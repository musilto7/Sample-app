package cz.musilto5.myflickerapp.presentation

import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
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
        composable(route = NAVIGATION_IMAGES_LIST) {
            ImagesScreen(navigateToImageDetail = {
                navigateToImageDetail(navController, it)
            })
        }
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
            val serializedImageVo =
                navBackStackEntry.arguments!!.getString(NAVIGATION_PARAM_IMAGE_DETAIL)!!
            ImageDetailScreen(NavUtils.fromJsonString(serializedImageVo),
                onBackPressed = {
                    navController.popBackStack()
                })
        }
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

const val NAVIGATION_IMAGES_LIST = "flickerImages"
const val NAVIGATION_PARAM_IMAGE_DETAIL = "imageDetail"
const val NAVIGATION_IMAGE_DETAIL =
    "flickerImages/detail?$NAVIGATION_PARAM_IMAGE_DETAIL={$NAVIGATION_PARAM_IMAGE_DETAIL}"
