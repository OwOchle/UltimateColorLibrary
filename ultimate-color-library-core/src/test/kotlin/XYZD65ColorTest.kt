import app.moreo.ucl.colors.HSLColor
import app.moreo.ucl.colors.RGBColor
import app.moreo.ucl.colors.XYZD65Color
import app.moreo.ucl.enums.ColorType
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class XYZD65ColorTest {

    private val red = RGBColor(255, 0, 0)
    private val xyzRed = XYZD65Color(0.4124f, 0.212f, 0.0193f)

    private val hslTestingColor = HSLColor(1.5151f, .427f, .569f)
    private val xyzTestingColor = XYZD65Color(0.3363f, 0.4506f, 0.1847f)

    @Test
    fun `any other to XYZ`() {
        assertEquals(xyzRed, red.toSpace(ColorType.XYZ_D65))

        assertEquals(xyzTestingColor, hslTestingColor.toSpace(ColorType.XYZ_D65))
    }

    @Test
    fun `XYZ to any other`() {
        assertEquals(red, xyzRed.toSpace(ColorType.RGB))
        assertEquals(hslTestingColor, xyzTestingColor.toSpace(ColorType.HSL))
    }
}