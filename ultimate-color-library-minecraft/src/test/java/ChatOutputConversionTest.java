import app.moreo.ucl.colors.SRGBColor;
import app.moreo.ucl.minecraft.ChatColorFormat;
import org.junit.jupiter.api.Test;

import static app.moreo.ucl.minecraft.ChatColorMinecraftConverterKt.toChatColor;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ChatOutputConversionTest {
    private final SRGBColor color = new SRGBColor(255, 241, 1);

    @Test
    public void rgbToChatColor() {
        assertEquals("&#fff101", toChatColor(color), "Ampersand is not correct");

        assertEquals("&x&f&f&f&1&0&1", toChatColor(color, ChatColorFormat.AMPERSAND_LEGACY), "Ampersand legacy is not correct");

        assertEquals("§x§f§f§f§1§0§1", toChatColor(color, ChatColorFormat.SECTION), "Section is not correct");
    }
}