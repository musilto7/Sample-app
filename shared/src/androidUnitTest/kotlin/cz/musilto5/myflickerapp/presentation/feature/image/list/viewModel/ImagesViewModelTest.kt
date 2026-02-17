package cz.musilto5.myflickerapp.presentation.feature.image.list.viewModel

import androidx.lifecycle.SavedStateHandle
import cz.musilto5.myflickerapp.domain.core.Error
import cz.musilto5.myflickerapp.domain.core.Result
import cz.musilto5.myflickerapp.domain.feature.images.model.FlickerImage
import cz.musilto5.myflickerapp.domain.feature.images.model.TagMode
import cz.musilto5.myflickerapp.domain.feature.images.repository.ImagesRepository
import cz.musilto5.myflickerapp.presentation.core.component.SwitchComponent
import cz.musilto5.myflickerapp.presentation.core.component.TextInputComponent
import cz.musilto5.myflickerapp.presentation.feature.image.ImageConstants
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Test
import kotlin.time.Clock

class ImagesViewModelTest {

    private val savedStateHandle = SavedStateHandle()
    private val textInputComponent: TextInputComponent =
        TextInputComponent("initialText", savedStateHandle, "textInput")
    private val switchComponent: SwitchComponent =
        SwitchComponent(savedStateHandle, ImageConstants.SWITCH_STATE_KEY, false)
    private lateinit var repository: ImagesRepository
    private lateinit var tested: ImagesViewModel


    @Test
    fun `verify that switch is switched of on the start`() = runTest {
        mockRepositoryReturnsSuccess()
        initViewModel()

        Assert.assertFalse(tested.switchComponent.checkedState.value)
    }

    private fun mockRepositoryReturnsSuccess() {
        repository = object : ImagesRepository {
            override suspend fun downloadImages(
                tags: List<String>,
                tagMode: TagMode
            ): Result<List<FlickerImage>, Error> {
                return Result.Success(IMAGES_LIST)
            }

        }
    }

    private fun initViewModel() {
        tested = ImagesViewModelImpl(
            textInputComponent = textInputComponent,
            switchComponent = switchComponent,
            repository = repository
        )
    }

    companion object {
        private val IMAGES_LIST = listOf(FlickerImage("title", "url", Clock.System.now()))
    }

}
