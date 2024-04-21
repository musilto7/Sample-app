package cz.musilto5.myflickerapp.presentation.core
import cz.musilto5.myflickerapp.presentation.feature.image.model.FlickerImageVO
import org.junit.Assert
import org.junit.Test

class NavUtilsTest {

    @Test
    fun toJsonString() {
        val imageVO = FlickerImageVO("title", "imageUrl ")
        val jsonString = NavUtils.toJsonString(imageVO)
        Assert.assertEquals(
            "{\"title\":\"title\",\"imageUrl\":\"imageUrl \"}",
            jsonString
        )
    }

    @Test
    fun fromJsonString() {
        val jsonString = "{\"title\":\"title\",\"imageUrl\":\"imageUrl\"}"
        val imageVO = NavUtils.fromJsonString<FlickerImageVO>(jsonString)
        Assert.assertEquals(FlickerImageVO(title = "title", imageUrl = "imageUrl"), imageVO)
    }
}