import app.moreo.ucl.Color
import app.moreo.ucl.colors.HSLColor
import app.moreo.ucl.enums.ColorType
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class HSLParseTest {
    // hsl(50 80% 40%)
    private val testingColor1 = HSLColor(50, 80, 40)

    // hsl(150deg 30% 60%)
    private val testingColor2 = HSLColor(150, 30, 60)

    // hsl(0.3turn 60% 45% / .7)
    private val testingColor3 = HSLColor(108, 60, 45, .7f)

    // hsl(0 80% 50% / 25%)
    private val testingColor4 = HSLColor(0, 80, 50, .25f)

    @Test
    fun `kotlin hsl parsing test`() {
        val color1 = Color.fromString("hsl(50 80% 40%)", ColorType.HSL)
        assertEquals(color1, testingColor1, "Color is not correct $color1")

        val color2 = Color.fromString("hsla(150deg 30% 60%)", ColorType.HSL)
        assertEquals(color2, testingColor2, "Color is not correct $color2")

        val color3 = Color.fromString("hsl(0.3turn 60% 45% / .7)", ColorType.HSL)
        assertEquals(color3, testingColor3, "Color is not correct $color3")

        val color4 = Color.fromString("hsla(0 80% 50% / 25%)", ColorType.HSL)
        assertEquals(color4, testingColor4, "Color is not correct $color4")
    }
}