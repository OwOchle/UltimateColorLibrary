package app.moreo.ucl.minecraft.adapters

import app.moreo.ucl.colors.SRGBColor
import net.minecraft.text.TextColor

/**
 * Converts a SRGBColor to a NMT TextColor
 * @return The TextColor
 */
fun SRGBColor.toTextColor(): TextColor {
    return TextColor.fromRgb(this.toInt())
}

/**
 * Converts a NMT TextColor to a SRGBColor
 * @return The SRGBColor
 */
fun TextColor.toSRGBColor(): SRGBColor {
    return SRGBColor.fromInt(this.rgb)
}