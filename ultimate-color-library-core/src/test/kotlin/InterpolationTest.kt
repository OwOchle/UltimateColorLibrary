import app.moreo.ucl.colors.SRGBColor
import app.moreo.ucl.enums.ColorType
import app.moreo.ucl.enums.InterpolationPath
import org.junit.jupiter.api.Test
import kotlin.test.assertTrue

class InterpolationTest {

    @Test
    fun `interpolate SRGB`() {
        val start = SRGBColor(0f, 0f, 0f)
        val end = SRGBColor(1f, 1f, 1f)

        val interpolatorShortest = start..end steps 5 path InterpolationPath.SHORTEST

        val interpolatorLongest = start..end steps 5 path InterpolationPath.LONGEST

        assertTrue { interpolatorShortest.toList() == listOf(
            SRGBColor(0f, 0f, 0f),
            SRGBColor(0.25f, 0.25f, 0.25f),
            SRGBColor(0.5f, 0.5f, 0.5f),
            SRGBColor(0.75f, 0.75f, 0.75f),
            SRGBColor(1f, 1f, 1f)
        ) }
    }
}