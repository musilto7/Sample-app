package cz.musilto5.myflickerapp.presentation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import cz.musilto5.myflickerapp.presentation.navigation.FlickerAppNavigation
import cz.musilto5.myflickerapp.presentation.theme.MyFlickerApplicationTheme

@Composable
fun App() {
    MyFlickerApplicationTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            FlickerAppNavigation()
        }
    }
}
