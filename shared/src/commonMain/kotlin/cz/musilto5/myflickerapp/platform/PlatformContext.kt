package cz.musilto5.myflickerapp.platform

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import io.ktor.client.engine.HttpClientEngine

expect fun createHttpClientEngine(): HttpClientEngine

expect fun isNetworkError(throwable: Throwable): Boolean

@Composable
expect fun NetworkImage(
    url: String,
    contentDescription: String?,
    modifier: Modifier,
    contentScale: ContentScale
)

@Composable
expect fun CloseNavigationIcon(
    contentDescription: String,
    onClick: () -> Unit
)
