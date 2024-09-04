package cz.musilto5.myflickerapp.presentation.feature.image.detail.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImage
import cz.musilto5.myflickerapp.R
import cz.musilto5.myflickerapp.presentation.feature.image.model.FlickerImageVO

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
            AsyncImage(
                model = imageVO.imageUrl,
                contentDescription = stringResource(id = R.string.image_content_description),
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
            CloseNavigationIcon(onBackPressed)
        },
        title = {
            AppBarTitle(imageVO)
        }
    )
}

@Composable
private fun CloseNavigationIcon(onBackPressed: () -> Unit) {
    IconButton(onClick = onBackPressed) {
        Icon(
            imageVector = Icons.Default.Close,
            contentDescription = stringResource(id = R.string.close_button_content_description)
        )
    }
}

@Composable
private fun AppBarTitle(imageVO: FlickerImageVO) {
    if (imageVO.title?.isNotEmpty() == true) {
        Text(imageVO.title)
    } else {
        Text(stringResource(id = R.string.image_detail_screen_title))
    }
}

@Composable
@Preview
fun ImageDetailScreenPreview() {
    ImageDetailScreen(
        FlickerImageVO(
            title = "Doggo",
            imageUrl = "https://live.staticflickr.com/65535/53668881651_13b3f9b891_m.jpg"
        )
    ) {}
}
