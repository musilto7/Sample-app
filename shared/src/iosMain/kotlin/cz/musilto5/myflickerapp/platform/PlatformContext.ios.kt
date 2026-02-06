package cz.musilto5.myflickerapp.platform

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.darwin.Darwin
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource

actual fun createHttpClientEngine(): HttpClientEngine = Darwin.create {}

actual fun isNetworkError(throwable: Throwable): Boolean {
    val message = throwable.message?.lowercase() ?: ""
    return message.contains("nsurlerror") ||
        message.contains("network") ||
        message.contains("connection") ||
        message.contains("host") ||
        throwable.cause?.let { isNetworkError(it) } == true
}

@Composable
actual fun NetworkImage(
    url: String,
    contentDescription: String?,
    modifier: Modifier,
    contentScale: ContentScale
) {
    KamelImage(
        resource = asyncPainterResource(data = url),
        contentDescription = contentDescription,
        modifier = modifier,
        contentScale = contentScale
    )
}

@Composable
actual fun CloseNavigationIcon(
    contentDescription: String,
    onClick: () -> Unit
) {
    IconButton(onClick = onClick) {
        Text(
            text = "←",
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}
