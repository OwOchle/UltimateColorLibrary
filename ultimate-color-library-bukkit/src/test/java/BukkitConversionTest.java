import app.moreo.ucl.colors.RGBColor;
import kotlin.test.junit5.JUnit5Asserter;
import net.md_5.bungee.api.ChatColor;
import org.junit.jupiter.api.Test;

import static app.moreo.ucl.adapters.ChatColorAdapterKt.toChatColor;
import static app.moreo.ucl.adapters.ChatColorAdapterKt.toColor;

public class BukkitConversionTest {

    private final RGBColor testingColor = new RGBColor((short) 150, (short) 192, (short) 98, 1);
    private final ChatColor chatColor = ChatColor.of("#96C062");

    @Test
    public void rgbToChatColor() {
        ChatColor color = toChatColor(testingColor);
        JUnit5Asserter.INSTANCE.assertEquals("RGB to ChatColor is wrong", color.getColor(), chatColor.getColor());
    }

    @Test
    public void chatColorToRGB() {
        RGBColor color = toColor(chatColor, RGBColor.TYPE);
        JUnit5Asserter.INSTANCE.assertEquals("ChatColor to RGB is wrong", color, testingColor);
    }
}
