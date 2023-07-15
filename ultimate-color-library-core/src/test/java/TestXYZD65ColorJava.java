import app.moreo.ucl.colors.HSLColor;
import app.moreo.ucl.colors.SRGBColor;
import app.moreo.ucl.colors.XYZD65Color;
import app.moreo.ucl.enums.ColorType;
import kotlin.test.junit5.JUnit5Asserter;
import org.junit.jupiter.api.Test;

public class TestXYZD65ColorJava {

    /*
     private val red = SRGBColor(255, 0, 0)
    private val xyzRed = XYZD65Color(0.4124f, 0.2126f,0.0193f)

    private val white = SRGBColor(255, 255, 255)
    private val xyzWhite = XYZD65Color(0.9505f, 1.0f, 1.089f)

    private val hslTestingColor = HSLColor(1.5151f, .427f, .569f)
    private val xyzTestingColor = XYZD65Color(0.3363f, 0.4506f, 0.1847f)
     */

    private final SRGBColor red = new SRGBColor((short) 255, (short) 0, (short) 0);
    private final XYZD65Color xyzRed = new XYZD65Color(0.4124f, 0.2126f, 0.0193f);

    private final SRGBColor white = new SRGBColor((short) 255, (short) 255, (short) 255);
    private final XYZD65Color xyzWhite = new XYZD65Color(0.9505f, 1.0f, 1.089f);

    private final HSLColor hslTestingColor = new HSLColor(1.5151f, .427f, .569f);
    private final XYZD65Color xyzTestingColor = new XYZD65Color(0.3363f, 0.4506f, 0.1847f);

    @Test
    public void otherToXyz() {
        JUnit5Asserter.INSTANCE.assertEquals("red to xyz", xyzRed, red.toSpace(ColorType.XYZ_D65));

        JUnit5Asserter.INSTANCE.assertEquals("white to xyz", xyzWhite, white.toSpace(ColorType.XYZ_D65));

        JUnit5Asserter.INSTANCE.assertEquals("hsl to xyz", xyzTestingColor, hslTestingColor.toSpace(ColorType.XYZ_D65));
    }

    @Test
    public void xyzToOther() {
        JUnit5Asserter.INSTANCE.assertEquals("redXyz to rgb", red, xyzRed.toSpace(ColorType.SRGB));

        JUnit5Asserter.INSTANCE.assertEquals("whiteXyz to rgb", xyzTestingColor, hslTestingColor.toSpace(ColorType.XYZ_D65));

        JUnit5Asserter.INSTANCE.assertEquals("xyzTesting to hsl", hslTestingColor, xyzTestingColor.toSpace(ColorType.HSL));
    }
}
