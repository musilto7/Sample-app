package cz.musilto5.myflickerapp.platform

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import coil.compose.AsyncImage
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.okhttp.OkHttp
import java.net.UnknownHostException
import javax.net.ssl.SSLHandshakeException

actual fun createHttpClientEngine(): HttpClientEngine = OkHttp.create {
    preconfigured = okhttp3.OkHttpClient.Builder().build()
}

actual fun isNetworkError(throwable: Throwable): Boolean =
    throwable is UnknownHostException || throwable is SSLHandshakeException

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
        contentScale = contentScale
    )
}

@Composable
actual fun CloseNavigationIcon(
    contentDescription: String,
    onClick: () -> Unit
) {
    IconButton(onClick = onClick) {
        Icon(
            imageVector = Icons.Default.Close,
            contentDescription = contentDescription
        )
    }
}
