import app.moreo.ucl.Color;
import app.moreo.ucl.colors.HSLColor;
import app.moreo.ucl.enums.ColorType;
import kotlin.test.junit5.JUnit5Asserter;
import org.junit.jupiter.api.Test;

class TestHSLParseJava {

    // hsl(50 80% 40%)
    private final HSLColor testingColor1 = new HSLColor(50, 80, 40);

    // hsl(150deg 30% 60%)
    private final HSLColor testingColor2 = new HSLColor(150, 30, 60);

    // hsl(0.3turn 60% 45% / .7)
    private final HSLColor testingColor3 = new HSLColor(108, 60, 45, .7f);

    // hsl(0 80% 50% / 25%)
    private final HSLColor testingColor4 = new HSLColor(0, 80, 50, .25f);

    @Test
    void hslParsingTest() {
        HSLColor color = Color.fromString("hsla(50 80% 40%)", ColorType.HSL);
        JUnit5Asserter.INSTANCE.assertEquals("HSL parsing wrong", testingColor1, color);

        color = Color.fromString("hsl(150deg 30% 60%)", ColorType.HSL);
        JUnit5Asserter.INSTANCE.assertEquals("HSL parsing wrong", testingColor2, color);

        color = Color.fromString("hsl(0.3turn 60% 45% / .7)", ColorType.HSL);
        JUnit5Asserter.INSTANCE.assertEquals("HSL parsing wrong", testingColor3, color);

        color = Color.fromString("hsla(0 80% 50% / 25%)", ColorType.HSL);
        JUnit5Asserter.INSTANCE.assertEquals("HSL parsing wrong", testingColor4, color);
    }
}