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
import cz.musilto5.myflickerapp.presentation.core.component.TextInputComponent
import cz.musilto5.myflickerapp.presentation.feature.image.list.model.ImagesViewState
import cz.musilto5.myflickerapp.presentation.feature.image.list.viewModel.ImagesScreenStateHolder
import cz.musilto5.myflickerapp.presentation.feature.image.model.FlickerImageVO
import cz.musilto5.myflickerapp.platform.NetworkImage
import cz.musilto5.myflickerapp.generated.resources.Res
import cz.musilto5.myflickerapp.generated.resources.image_content_description
import cz.musilto5.myflickerapp.generated.resources.reload
import org.jetbrains.compose.resources.stringResource

@Composable
fun ImagesScreen(
    stateHolder: ImagesScreenStateHolder,
    navigateToImageDetail: (FlickerImageVO) -> Unit,
) {
    val viewState by stateHolder.viewStates.collectAsState()
    val isSwitchChecked by stateHolder.switchState.collectAsState()

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
    navigateToImageDetail: (FlickerImageVO) -> Unit
) {
    Column {
        Row(verticalAlignment = Alignment.CenterVertically) {
            val textState by textInputComponent.viewState.collectAsState()
            TextField(
                value = textState.text,
                onValueChange = textInputComponent::updateText,
                modifier = Modifier
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
private fun Images(
    viewState: ImagesViewState,
    navigateToImageDetail: (FlickerImageVO) -> Unit
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
    reloadImages: () -> Unit
) {
    if (viewState.isLoading) {
        CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
    }
    viewState.errorMessage?.let { errorMessage ->
        Column(
            modifier = Modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = errorMessage)
            Button(onClick = reloadImages) {
                Text(stringResource(Res.string.reload))
            }
        }
    }
}
