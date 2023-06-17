import app.moreo.ucl.Color;
import app.moreo.ucl.colors.HSLColor;
import app.moreo.ucl.colors.RGBColor;
import app.moreo.ucl.enums.ColorType;
import kotlin.test.junit5.JUnit5Asserter;
import org.junit.jupiter.api.Test;

public class TestPrimaryJava {

    private final RGBColor testingColor = new RGBColor((short) 150, (short) 192, (short) 98, 1);
    private final RGBColor shortHexColor = new RGBColor((short) 153, (short) 204, (short) 0, 1);

    @Test
    public void primaryConversionTest() {
        HSLColor color = testingColor.toSpace(ColorType.HSL);
        JUnit5Asserter.INSTANCE.assertEquals("HSL conversion wrong", color.getDegreesHue(), 87);
    }

    @Test
    public void longHexParsingTest() {
        RGBColor color = Color.fromString("#96C062", ColorType.RGB);
        assert color.equals(testingColor);
    }

    @Test
    public void shortHexParsingTest() {
        RGBColor color = Color.fromString("#9C0", ColorType.RGB);
        assert color.equals(shortHexColor);
    }
}
