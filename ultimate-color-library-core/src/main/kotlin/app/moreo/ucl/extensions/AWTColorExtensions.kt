package app.moreo.ucl.extensions

import app.moreo.ucl.colors.SRGBColor
import java.awt.Color

/**
 * Converts a java.awt.Color to a SRGBColor
 * @return The SRGBColor
 */
fun Color.toSRGBColor(): SRGBColor {
    return SRGBColor.fromAWTColor(this)
}