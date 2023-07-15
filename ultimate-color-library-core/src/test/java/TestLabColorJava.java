import app.moreo.ucl.colors.LabColor;
import app.moreo.ucl.colors.XYZD65Color;
import app.moreo.ucl.enums.ColorType;
import kotlin.test.junit5.JUnit5Asserter;
import org.junit.jupiter.api.Test;

public class TestLabColorJava {

    private final XYZD65Color xyzRed = new XYZD65Color(0.4124f, 0.2126f, 0.0193f);

    private final LabColor labRed = new LabColor(53.23f, 80.109f, 67.22f);

    private final XYZD65Color xyzGreenish = new XYZD65Color(0.2032f, 0.3574f, 0.0840f);

    private final LabColor labGreenish = new LabColor(66.32f, -55.85f, 56.79f);

    @Test
    public void labToXyz() {
        JUnit5Asserter.INSTANCE.assertEquals("lab red to xyz", xyzRed, labRed.toSpace(ColorType.XYZ_D65));

        JUnit5Asserter.INSTANCE.assertEquals("lab greenish to xyz", xyzGreenish, labGreenish.toSpace(ColorType.XYZ_D65));
    }

    @Test
    public void xyzToLab() {
        JUnit5Asserter.INSTANCE.assertEquals("xyz red to lab", labRed, xyzRed.toSpace(ColorType.LAB));

        JUnit5Asserter.INSTANCE.assertEquals("xyz greenish to lab", labGreenish, xyzGreenish.toSpace(ColorType.LAB));
    }
}
