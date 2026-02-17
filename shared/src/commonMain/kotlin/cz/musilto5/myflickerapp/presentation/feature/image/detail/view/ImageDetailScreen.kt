package cz.musilto5.myflickerapp.presentation.feature.image.detail.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import cz.musilto5.myflickerapp.platform.CloseNavigationIcon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import cz.musilto5.myflickerapp.presentation.feature.image.model.FlickerImageVO
import cz.musilto5.myflickerapp.platform.NetworkImage
import cz.musilto5.myflickerapp.generated.resources.Res
import cz.musilto5.myflickerapp.generated.resources.close_button_content_description
import cz.musilto5.myflickerapp.generated.resources.image_content_description
import cz.musilto5.myflickerapp.generated.resources.image_detail_screen_title
import cz.musilto5.myflickerapp.presentation.feature.image.preview.ImagesScreenPreviews
import cz.musilto5.myflickerapp.presentation.theme.MyFlickerApplicationTheme
import org.jetbrains.compose.resources.stringResource

@Composable
fun ImageDetailScreen(imageVO: FlickerImageVO, onBackPressed: () -> Unit) {
    Scaffold(
        topBar = {
            ImageDetailTopAppBar(onBackPressed, imageVO)
        },
    ) { paddingValues ->
        Column(
            modifier = Modifier.verticalScroll(rememberScrollState())
        ) {
            NetworkImage(
                url = imageVO.imageUrl,
                contentDescription = stringResource(Res.string.image_content_description),
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxWidth(),
                contentScale = ContentScale.FillWidth
            )
        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun ImageDetailTopAppBar(
    onBackPressed: () -> Unit,
    imageVO: FlickerImageVO
) {
    TopAppBar(
        navigationIcon = {
            CloseNavigationIcon(
                contentDescription = stringResource(Res.string.close_button_content_description),
                onClick = onBackPressed
            )
        },
        title = {
            if (!imageVO.title.isNullOrEmpty()) {
                Text(imageVO.title)
            } else {
                Text(stringResource(Res.string.image_detail_screen_title))
            }
        }
    )
}

@Preview
@Composable
private fun ImageDetailScreenPreview() {
    MyFlickerApplicationTheme {
        ImageDetailScreen(
            imageVO = ImagesScreenPreviews.sampleImages[0],
            onBackPressed = {}
        )
    }
}

@Preview
@Composable
private fun ImageDetailScreenDarkPreview() {
    MyFlickerApplicationTheme(darkTheme = true) {
        ImageDetailScreen(
            imageVO = ImagesScreenPreviews.sampleImages[0],
            onBackPressed = {}
        )
    }
}

@Preview
@Composable
private fun ImageDetailScreenNoTitlePreview() {
    MyFlickerApplicationTheme {
        ImageDetailScreen(
            imageVO = ImagesScreenPreviews.sampleImages[4],
            onBackPressed = {}
        )
    }
}
