import app.moreo.ucl.colors.LabColor
import app.moreo.ucl.colors.XYZD65Color
import app.moreo.ucl.enums.ColorType
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

// No need to test from Lab to any other because you need to convert to XYZ in order to convert to any other
class LabColorTest {
    private val xyzRed = XYZD65Color(0.4124f, 0.2126f, 0.0193f)

    private val labRed = LabColor(53.23f, 80.109f, 67.22f)

    // hsl(108deg 60% 45%)
    private val xyzGreenish = XYZD65Color(0.2032f, 0.3574f, 0.0840f)

    private val labGreenish = LabColor(66.32f, -55.85f, 56.79f)

    @Test
    fun `lab to XYZ`() {

        val xyzRedFromLab = labRed.toSpace(ColorType.XYZ_D65)
        assertEquals(xyzRed, xyzRedFromLab, "XYZ red is not correct")

        val xyzGreenishFromLab = labGreenish.toSpace(ColorType.XYZ_D65)
        assertEquals(xyzGreenish, xyzGreenishFromLab, "XYZ greenish is not correct")
    }

    @Test
    fun `XYZ to lab`() {
        val labRedFromXYZ = xyzRed.toSpace(ColorType.LAB)
        assertEquals(labRed, labRedFromXYZ, "Lab red is not correct")

        val labGreenishFromXYZ = xyzGreenish.toSpace(ColorType.LAB)
        assertEquals(labGreenish, labGreenishFromXYZ, "Lab greenish is not correct")
    }
}