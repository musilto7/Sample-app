package cz.musilto5.myflickerapp.presentation.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.navigation3.ui.NavDisplay

object NavigationTransitions {
    const val DURATION_MS = 300
    
    val slideUpEnter = slideInVertically(
        initialOffsetY = { it },
        animationSpec = tween(DURATION_MS)
    )
    
    val slideDownExit = slideOutVertically(
        targetOffsetY = { it },
        animationSpec = tween(DURATION_MS)
    )
    
    fun detailScreenTransitions() = 
        NavDisplay.transitionSpec {
            slideUpEnter togetherWith ExitTransition.KeepUntilTransitionsFinished
        } + NavDisplay.popTransitionSpec {
            EnterTransition.None togetherWith slideDownExit
        } + NavDisplay.predictivePopTransitionSpec {
            EnterTransition.None togetherWith slideDownExit
        }
}
