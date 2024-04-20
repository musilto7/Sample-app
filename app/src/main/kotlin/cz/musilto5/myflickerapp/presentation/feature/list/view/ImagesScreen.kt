package cz.musilto5.myflickerapp.presentation.feature.list.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import cz.musilto5.myflickerapp.presentation.feature.list.ImagesViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun ImagesScreen(viewModel: ImagesViewModel = koinViewModel()) {

    val viewState by viewModel.viewStates.collectAsState()

    Column {
        val textInputComponent = viewModel.textInputComponent
        val textInputViewState by textInputComponent.viewState.collectAsState()
        TextField(
            value = textInputViewState.text,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            onValueChange = textInputComponent::updateText
        )

        LazyVerticalStaggeredGrid(
            columns = StaggeredGridCells.Adaptive(minSize = 150.dp),
            modifier = Modifier.padding(horizontal = 12.dp)
        ) {
            items(viewState.images,
                key = { it.imageUrl },
                contentType = { "1" }) { item ->
                AsyncImage(
                    model = item.imageUrl,
                    contentDescription = null,
                    modifier = Modifier
                        .padding(4.dp),
                    contentScale = ContentScale.FillWidth,
                )
            }
        }
    }

}