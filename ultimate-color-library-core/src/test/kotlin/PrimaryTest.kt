import app.moreo.ucl.Color
import app.moreo.ucl.colors.SRGBColor
import app.moreo.ucl.enums.ColorType
import app.moreo.ucl.extensions.toSRGBColor
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class PrimaryTest {

    private val testingColor = SRGBColor(150, 192, 98)
    private val shortHexColor = SRGBColor(153, 204, 0, .8f)

    @Test
    fun `kotlin space conversion test`() {
        val hsl = testingColor.toSpace(ColorType.HSL)
        assertEquals(hsl.degreesHue, 87, "Hue is not correct")
        assertEquals(hsl.saturation.toDouble(), .427, 0.01, "Saturation is not correct")
        assertEquals(hsl.lightness.toDouble(), .569, .01, "Lightness is not correct")
    }

    @Test
    fun `kotlin long hex parsing test`() {
        val color = Color.fromString("#96C062", ColorType.SRGB)
        assertEquals(color, testingColor, "Color is not correct $color")
    }

    @Test
    fun `kotlin short hex parsing test`() {
        val color = Color.fromString("#9C0C", ColorType.SRGB)
        assertEquals(color, shortHexColor, "Color is not correct $color")
    }

    @Test
    fun `kotlin rgb parsing test`() {
        val color = Color.fromString("rgb(150, 192, 98)", ColorType.SRGB)
        assertEquals(color, testingColor, "Color is not correct $color")

        val color2 = Color.fromString("rgb(153 204 0 / .8)", ColorType.SRGB)
        assertEquals(color2, shortHexColor, "Color is not correct $color2")
    }

    @Test
    fun `kotlin integer to color conversion test`() {
        val color = SRGBColor.fromInt(9879650)
        assertEquals(testingColor, color, "Color is not correct $color")

        val copied = shortHexColor.copy()
        copied.alpha = 1f

        val color2 = 10079232.toSRGBColor()
        assertEquals(copied, color2, "Color is not correct $color2")
    }

    @Test
    fun `kotlin color to integer conversion test`() {
        val color = testingColor.toInt()
        assertEquals(9879650, color, "Color is not correct $color")

        val color2 = shortHexColor.toInt()
        assertEquals(10079232, color2, "Color is not correct $color2")
    }
}
