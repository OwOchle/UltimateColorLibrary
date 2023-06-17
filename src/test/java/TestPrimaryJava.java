import app.moreo.ucl.colors.RGBColor;
import app.moreo.ucl.enums.ColorType;
import kotlin.test.junit5.JUnit5Asserter;
import org.junit.jupiter.api.Test;

public class TestPrimaryJava {

    @Test
    public void primaryConversionTest() {
        RGBColor color = new RGBColor(150, 192, 98, 0);
        JUnit5Asserter.INSTANCE.assertEquals("", color.toColor(ColorType.HSL).getHue(), 87.04);
    }
}
