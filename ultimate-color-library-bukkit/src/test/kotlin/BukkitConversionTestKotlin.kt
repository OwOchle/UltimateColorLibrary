import app.moreo.ucl.adapters.toChatColor
import app.moreo.ucl.adapters.toColor
import app.moreo.ucl.colors.RGBColor
import net.md_5.bungee.api.ChatColor
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class BukkitConversionTestKotlin {

    private val testingColor = RGBColor(150, 192, 98)
    private val chatColor = ChatColor.of("#96C062")

    @Test
    fun `convert from RGBColor to ChatColor`() {
        val converted = testingColor.toChatColor()
        assertEquals(converted.color, chatColor.color, "Color is not correct $converted")
    }

    @Test
    fun `convert from ChatColor to RGBColor`() {
        val converted = chatColor.toColor(RGBColor.TYPE)
        assertEquals(converted, testingColor, "Color is not correct $converted")
    }
}