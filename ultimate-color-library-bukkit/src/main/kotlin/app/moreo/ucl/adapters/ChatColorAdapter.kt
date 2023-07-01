package app.moreo.ucl.adapters

import app.moreo.ucl.Color
import app.moreo.ucl.colors.SRGBColor
import app.moreo.ucl.enums.ColorType
import net.md_5.bungee.api.ChatColor

/**
 * Converts a [Color] to a Bungee [ChatColor]
 * (bukkit ChatColor are not creatable using SRGB values)
 * @return The Bungee [ChatColor]
 */
fun Color.toChatColor(): ChatColor {
    this.toSpace(ColorType.SRGB).apply {
        return ChatColor.of(java.awt.Color(red, green, blue, alpha))
    }
}

/**
 * Converts a Bungee [ChatColor] to a [Color]
 * @param type The wanted output space
 * @return The [Color]
 */
fun <T: Color> ChatColor.toColor(type: ColorType<T>): T {
    this.color.apply {
        return SRGBColor(red.toShort(), green.toShort(), blue.toShort(), alpha.div(255f)).toSpace(type)
    }
}