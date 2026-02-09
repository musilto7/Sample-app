package cz.musilto5.myflickerapp.platform

import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import coil3.compose.AsyncImage
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.darwin.Darwin

// TODO refactor this file
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
    AsyncImage(
        model = url,
        contentDescription = contentDescription,
        modifier = modifier,
        contentScale = contentScale,
        placeholder = ColorPainter(MaterialTheme.colorScheme.surfaceVariant),
        error = ColorPainter(MaterialTheme.colorScheme.errorContainer),
        fallback = ColorPainter(MaterialTheme.colorScheme.surfaceVariant)
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
