import app.moreo.ucl.colors.SRGBColor
import app.moreo.ucl.minecraft.ChatColorFormat
import app.moreo.ucl.minecraft.toChatColor
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class MinecraftColorFormatTest {
    private val color = SRGBColor(255, 241, 1)

    @Test
    fun `test export colors to string`() {
        assertEquals("&#fff101", color.toChatColor(), "Ampersand is not correct")

        assertEquals("&x&f&f&f&1&0&1", color.toChatColor(ChatColorFormat.AMPERSAND_LEGACY), "Ampersand legacy is not correct")

        assertEquals("§x§f§f§f§1§0§1", color.toChatColor(ChatColorFormat.SECTION), "Section is not correct")
    }
}