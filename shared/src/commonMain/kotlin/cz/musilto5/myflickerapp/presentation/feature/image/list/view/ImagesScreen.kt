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
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cz.musilto5.myflickerapp.presentation.core.component.TextInputComponent
import cz.musilto5.myflickerapp.presentation.feature.image.list.model.ImagesViewState
import cz.musilto5.myflickerapp.presentation.feature.image.model.FlickerImageVO
import cz.musilto5.myflickerapp.platform.NetworkImage
import cz.musilto5.myflickerapp.generated.resources.Res
import cz.musilto5.myflickerapp.generated.resources.image_content_description
import cz.musilto5.myflickerapp.generated.resources.reload
import cz.musilto5.myflickerapp.presentation.feature.image.list.viewModel.ImagesViewModel
import cz.musilto5.myflickerapp.presentation.feature.image.preview.ImagesScreenPreviewData
import cz.musilto5.myflickerapp.presentation.theme.MyFlickerApplicationTheme
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun ImagesScreen(
    viewModel: ImagesViewModel = koinViewModel(),
    navigateToImageDetail: (FlickerImageVO) -> Unit,
) {
    val stateHolder = viewModel.stateHolder
    val viewState by stateHolder.viewStates.collectAsStateWithLifecycle()
    val isSwitchChecked by stateHolder.switchState.collectAsStateWithLifecycle()

    ImageScreen(
        viewState = viewState,
        textInputComponent = stateHolder.textInputComponent,
        isSwitchChecked = isSwitchChecked,
        onSwitchCheckedChange = stateHolder::onSwitchCheckedChange,
        reloadImages = stateHolder::reloadImages,
        navigateToImageDetail = navigateToImageDetail,
    )
}

@Composable
private fun ImageScreen(
    viewState: ImagesViewState,
    textInputComponent: TextInputComponent,
    isSwitchChecked: Boolean,
    onSwitchCheckedChange: (Boolean) -> Unit,
    reloadImages: () -> Unit,
    navigateToImageDetail: (FlickerImageVO) -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            val textState by textInputComponent.viewState.collectAsState()
            TextField(
                value = textState.text,
                onValueChange = textInputComponent::updateText,
                modifier = Modifier.weight(1f),
            )
            Switch(
                checked = isSwitchChecked,
                onCheckedChange = onSwitchCheckedChange,
                modifier = Modifier.padding(start = 16.dp)
            )
        }
        Box(modifier = Modifier.fillMaxSize()) {
            Images(viewState, navigateToImageDetail)
            ProgressInfo(viewState, reloadImages)
        }
    }
}

@Composable
private fun Images(
    viewState: ImagesViewState,
    navigateToImageDetail: (FlickerImageVO) -> Unit,
) {
    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Adaptive(minSize = 150.dp),
        modifier = Modifier.padding(horizontal = 4.dp)
    ) {
        items(
            items = viewState.images,
            key = { it.imageUrl },
            contentType = { "image" }
        ) { item ->
            NetworkImage(
                url = item.imageUrl,
                contentDescription = stringResource(Res.string.image_content_description),
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
                    .padding(4.dp)
                    .clickable { navigateToImageDetail(item) },
                contentScale = ContentScale.Crop
            )
        }
    }
}

@Composable
private fun BoxScope.ProgressInfo(
    viewState: ImagesViewState,
    reloadImages: () -> Unit,
) {
    if (viewState.isLoading) {
        CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
    }
    viewState.errorResource?.let { errorResource ->
        Column(
            modifier = Modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = stringResource(errorResource))
            Button(onClick = reloadImages) {
                Text(stringResource(Res.string.reload))
            }
        }
    }
}

@Preview
@Composable
private fun ImagesScreenPreview() {
    MyFlickerApplicationTheme {
        ImageScreen(
            viewState = ImagesScreenPreviewData.loadedViewState,
            textInputComponent = ImagesScreenPreviewData.createPreviewTextInputComponent("nature"),
            isSwitchChecked = false,
            onSwitchCheckedChange = {},
            reloadImages = {},
            navigateToImageDetail = {}
        )
    }
}

@Preview
@Composable
private fun ImagesScreenDarkPreview() {
    MyFlickerApplicationTheme(darkTheme = true) {
        ImageScreen(
            viewState = ImagesScreenPreviewData.loadedViewState,
            textInputComponent = ImagesScreenPreviewData.createPreviewTextInputComponent("nature"),
            isSwitchChecked = false,
            onSwitchCheckedChange = {},
            reloadImages = {},
            navigateToImageDetail = {}
        )
    }
}

@Preview
@Composable
private fun ImagesScreenLoadingPreview() {
    MyFlickerApplicationTheme {
        ImageScreen(
            viewState = ImagesScreenPreviewData.loadingViewState,
            textInputComponent = ImagesScreenPreviewData.createPreviewTextInputComponent("loading"),
            isSwitchChecked = true,
            onSwitchCheckedChange = {},
            reloadImages = {},
            navigateToImageDetail = {}
        )
    }
}

@Preview
@Composable
private fun ImagesScreenErrorPreview() {
    MyFlickerApplicationTheme {
        ImageScreen(
            viewState = ImagesScreenPreviewData.errorViewState,
            textInputComponent = ImagesScreenPreviewData.createPreviewTextInputComponent("error"),
            isSwitchChecked = false,
            onSwitchCheckedChange = {},
            reloadImages = {},
            navigateToImageDetail = {}
        )
    }
}
