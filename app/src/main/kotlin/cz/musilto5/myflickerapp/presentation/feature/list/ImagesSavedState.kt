package cz.musilto5.myflickerapp.presentation.feature.list

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ImagesSavedState(
    val tagsInput: String,
): Parcelable {

    companion object {
        val IDLE = ImagesSavedState("")
    }
}