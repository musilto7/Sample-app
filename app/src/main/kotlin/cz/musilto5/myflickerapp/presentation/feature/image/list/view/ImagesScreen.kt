package cz.musilto5.myflickerapp.presentation.feature.image.list.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import cz.musilto5.myflickerapp.R
import cz.musilto5.myflickerapp.presentation.core.component.TextInputComponent
import cz.musilto5.myflickerapp.presentation.core.component.tooling.TextInputComponentStub
import cz.musilto5.myflickerapp.presentation.feature.image.list.model.ImagesViewState
import cz.musilto5.myflickerapp.presentation.feature.image.list.viewModel.ImagesViewModel
import cz.musilto5.myflickerapp.presentation.feature.image.model.FlickerImageVO
import org.koin.androidx.compose.koinViewModel

@Composable
fun ImagesScreen(
    navigateToImageDetail: (FlickerImageVO) -> Unit,
    viewModel: ImagesViewModel = koinViewModel()
) {

    val viewState by viewModel.viewStates.collectAsState()
    val isSwitchChecked by viewModel.switchState.collectAsState()

    ImageScreen(
        viewState,
        viewModel.textInputComponent,
        isSwitchChecked,
        viewModel::onSwitchCheckedChange,
        viewModel::reloadImages,
        navigateToImageDetail,
    )
}

@Composable
private fun ImageScreen(
    viewState: ImagesViewState,
    textInputComponent: TextInputComponent,
    isSwitchChecked: Boolean,
    onSwitchCheckedChange: (Boolean) -> Unit,
    reloadImages: () -> Unit,
    navigateToImageDetail: (FlickerImageVO) -> Unit
) {
    Column {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextFiled(
                textInputComponent,
                Modifier
                    .padding(16.dp)
                    .weight(1f),
            )

            Switch(
                checked = isSwitchChecked,
                onCheckedChange = onSwitchCheckedChange,
                modifier = Modifier.padding(end = 16.dp)
            )

        }

        Box(modifier = Modifier.fillMaxSize()) {
            Images(viewState, navigateToImageDetail)
            ProgressInfo(viewState, reloadImages)
        }
    }
}

@Composable
private fun TextFiled(
    textInputComponent: TextInputComponent,
    modifier: Modifier = Modifier,
) {
    val textInputViewState by textInputComponent.viewState.collectAsState()
    TextField(
        value = textInputViewState.text,
        modifier = modifier,
        onValueChange = textInputComponent::updateText
    )
}

@Composable
private fun Images(
    viewState: ImagesViewState,
    navigateToImageDetail: (FlickerImageVO) -> Unit
) {
    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Adaptive(minSize = 150.dp),
        modifier = Modifier.padding(horizontal = 12.dp)
    ) {
        items(
            viewState.images,
            key = { it.imageUrl },
            contentType = { "1" }) { item ->
            AsyncImage(
                model = item.imageUrl,
                contentDescription = stringResource(id = R.string.image_content_description),
                modifier = Modifier
                    .padding(4.dp)
                    .clickable { navigateToImageDetail(item) },
                contentScale = ContentScale.FillWidth,
            )
        }
    }
}

@Composable
private fun BoxScope.ProgressInfo(
    viewState: ImagesViewState,
    reloadImages: () -> Unit
) {
    if (viewState.isLoading) {
        CircularProgressIndicator(modifier = Modifier.Companion.align(Alignment.Center))
    }

    viewState.errorMessage?.let { errorMessage ->
        Column(
            modifier = Modifier.Companion.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = errorMessage)
            Button(onClick = reloadImages) {
                Text(stringResource(id = R.string.reload))
            }
        }
    }
}

@Composable
@Preview
fun ImagesScreenPreview() {
    ImageScreen(
        viewState = ImagesViewState(
            images = listOf(
                FlickerImageVO(
                    title = null,
                    imageUrl = "https://live.staticflickr.com/65535/53669199253_10dd034e5d_m.jpg"
                ),
                FlickerImageVO(
                    title = null,
                    imageUrl = "https://live.staticflickr.com/65535/53669243224_c94a116284_m.jpg"
                ),
                FlickerImageVO(
                    title = null,
                    imageUrl = "https://live.staticflickr.com/65535/53668017662_4646570200_m.jpg"
                ),
                FlickerImageVO(
                    title = null,
                    imageUrl = "https://live.staticflickr.com/65535/53668007792_87b279abbe_m.jpg"
                ),

                FlickerImageVO(
                    title = null,
                    imageUrl = "https://live.staticflickr.com/65535/53669341310_e942d48c08_m.jpg"
                ),
            ),
            isLoading = false,
            errorMessage = null,
            tagsInput = "dog",
        ),
        textInputComponent = TextInputComponentStub("dog"),
        isSwitchChecked = false,
        onSwitchCheckedChange = {},
        reloadImages = {},
        navigateToImageDetail = {}
    )
}