package app.moreo.ucl.extensions

import app.moreo.ucl.colors.SRGBColor

/**
 * Converts an integer to a SRGBColor
 * @return The color
 */
fun Int.toSRGBColor(): SRGBColor {
    return SRGBColor.fromInt(this)
}