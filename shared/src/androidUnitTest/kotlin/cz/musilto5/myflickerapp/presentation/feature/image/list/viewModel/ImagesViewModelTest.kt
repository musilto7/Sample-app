package cz.musilto5.myflickerapp.presentation.feature.image.list.viewModel

import androidx.lifecycle.SavedStateHandle
import cz.musilto5.myflickerapp.domain.core.Result
import cz.musilto5.myflickerapp.domain.feature.images.model.FlickerImage
import cz.musilto5.myflickerapp.domain.feature.images.model.TagMode
import cz.musilto5.myflickerapp.domain.feature.images.repository.ImagesRepository
import cz.musilto5.myflickerapp.presentation.ImagesViewModel
import cz.musilto5.myflickerapp.presentation.core.component.TextInputComponent
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Test
import java.time.OffsetDateTime


class ImagesViewModelTest {

    private val savedStateHandle = SavedStateHandle()
    private val textInputComponent: TextInputComponent =
        TextInputComponent("initialText", savedStateHandle, "textInput")
    private lateinit var repository: ImagesRepository
    private lateinit var tested: ImagesViewModel


    @Test
    fun `verify that switch is switched of on the start`() = runTest {
        mockRepositoryReturnsSuccess()
        initViewModel()

        Assert.assertFalse(tested.stateHolder.switchState.value)
    }

    private fun mockRepositoryReturnsSuccess() {
        repository = object : ImagesRepository {
            override suspend fun downloadImages(
                tags: List<String>,
                tagMode: TagMode
            ): Result<List<FlickerImage>> {
                return Result.Success(IMAGES_LIST)
            }

        }
    }

    private fun initViewModel() {
        tested = ImagesViewModel(repository, savedStateHandle, textInputComponent)
    }

    companion object {
        private val IMAGES_LIST = listOf(FlickerImage("title", "url", OffsetDateTime.now()))
    }

}