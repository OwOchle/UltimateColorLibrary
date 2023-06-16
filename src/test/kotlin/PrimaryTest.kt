import app.moreo.ucl.colors.RGBColor
import app.moreo.ucl.enums.ColorType
import org.junit.jupiter.api.Test
import kotlin.math.round
import kotlin.test.assertEquals

class PrimaryTest {

    @Test
    fun `test kotlin conversion`() {
        val color = RGBColor(150, 192, 98)
        val hsl = color.toColor(ColorType.HSL)
        assertEquals(round(hsl.degreesHue.toDouble()), 86.0, .1, "Hue is not correct")
        assertEquals(hsl.saturation.toDouble(), .427, 0.01, "Saturation is not correct")
        assertEquals(hsl.lightness.toDouble(), .569, .01, "Lightness is not correct")
    }
}
