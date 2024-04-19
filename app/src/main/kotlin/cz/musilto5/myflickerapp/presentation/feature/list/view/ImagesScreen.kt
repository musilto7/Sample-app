package cz.musilto5.myflickerapp.presentation.feature.list.view

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import cz.musilto5.myflickerapp.presentation.feature.list.ImagesViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun ImagesScreen(viewModel: ImagesViewModel = koinViewModel()) {

    val viewState by viewModel.viewStates.collectAsState()

    Text(text = viewState.toString())

}