import app.moreo.ucl.colors.RGBColor
import app.moreo.ucl.enums.ColorType
import app.moreo.ucl.interfaces.Color
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class PrimaryTest {

    private val testingColor = RGBColor(150, 192, 98)
    private val shortHexColor = RGBColor(153, 204, 0, .8f)

    @Test
    fun `kotlin space conversion test`() {
        val hsl = testingColor.toSpace(ColorType.HSL)
        assertEquals(hsl.degreesHue, 87, "Hue is not correct")
        assertEquals(hsl.saturation.toDouble(), .427, 0.01, "Saturation is not correct")
        assertEquals(hsl.lightness.toDouble(), .569, .01, "Lightness is not correct")
    }

    @Test
    fun `kotlin long hex parsing test`() {
        val color = Color.fromString("#96C062", ColorType.RGB)
        assertEquals(color, testingColor, "Color is not correct $color")
    }

    @Test
    fun `kotlin short hex parsing test`() {
        val color = Color.fromString("#9C0C", ColorType.RGB)
        assertEquals(color, shortHexColor, "Color is not correct $color")
    }

    @Test
    fun `kotlin rgb parsing test`() {
        val color = Color.fromString("rgb(150, 192, 98)", ColorType.RGB)
        assertEquals(color, testingColor, "Color is not correct $color")

        val color2 = Color.fromString("rgb(153 204 0 / .8)", ColorType.RGB)
        assertEquals(color2, shortHexColor, "Color is not correct $color2")
    }
}
