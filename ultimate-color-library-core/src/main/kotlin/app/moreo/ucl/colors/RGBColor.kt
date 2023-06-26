package app.moreo.ucl.colors

import app.moreo.ucl.enums.ColorType
import app.moreo.ucl.enums.RGBFormats
import app.moreo.ucl.exceptions.ColorTypeException
import app.moreo.ucl.exceptions.GamutException
import app.moreo.ucl.interfaces.CSSExportable
import app.moreo.ucl.interfaces.CSSExportableMultipleFormats
import app.moreo.ucl.interfaces.Color
import app.moreo.ucl.interfaces.Interpolatable
import app.moreo.ucl.interpolation.ColorInterpolation
import app.moreo.ucl.utils.toRadians
import kotlin.math.abs

/**
 * RGB color representation
 * @property red red between 0 and 1
 * @property green green between 0 and 1
 * @property blue blue between 0 and 1
 *
 * @param red red between 0 and 1
 * @param green green between 0 and 1
 * @param blue blue between 0 and 1
 * @param alpha alpha between 0 and 1
 */
class RGBColor(var red: Float, var green: Float, var blue: Float, override var alpha: Float = 1f): Color, Interpolatable<RGBColor>, CSSExportableMultipleFormats<RGBFormats> {

    companion object {
        @JvmField
        val TYPE = ColorType.RGB
    }

    /**
     * RGB color representation
     * @property red red between 0 and 1
     * @property green green between 0 and 1
     * @property blue blue between 0 and 1
     *
     * @param red red between 0 and 255
     * @param green green between 0 and 255
     * @param blue blue between 0 and 255
     * @param alpha alpha between 0 and 1
     */
    constructor(red: Short, green: Short, blue: Short, alpha: Float = 1f) : this(red / 255f, green / 255f, blue / 255f, alpha) {
        if (minOf(red, green, blue) < 0 || maxOf(red, green, blue) > 255) throw GamutException("RGB values must be between 0 and 255")
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : Color> toSpace(color: ColorType<T>): T {
        return when (color) {
            ColorType.HSV, ColorType.HSB -> {
                // Hue is in degrees
                val value = maxOf(red, green, blue)
                val xMin = minOf(red, green, blue)
                val chroma = value - xMin

                val hue = when {
                    chroma == 0f -> 0f
                    value == red -> 60 * ((green - blue) / chroma).mod(6f)
                    value == green -> 60 * (((blue - red) / chroma) + 2)
                    else -> 60 * (((red - green) / chroma) + 4)
                }

                val saturation = when (value) {
                    0f -> 0f
                    else -> chroma / value
                }

                return HSVColor(hue.toRadians(), saturation, value) as T
            }
            ColorType.HSL -> {
                val xMax = maxOf(red, green, blue)
                val xMin = minOf(red, green, blue)
                val chroma = xMax - xMin
                val lightness = (xMax + xMin) / 2

                val hue = when {
                    chroma == 0f -> 0f
                    xMax == red -> 60 * ((green - blue) / chroma).mod(6f)
                    xMax == green -> 60 * (((blue - red) / chroma) + 2)
                    else -> 60 * (((red - green) / chroma) + 4)
                }

                val saturation = when (lightness) {
                    0f, 1f -> 0f
                    else -> chroma / (1 - abs(2 * lightness - 1))
                }

                return HSLColor(hue.toRadians(), saturation, lightness) as T
            }
            ColorType.RGB -> {
                this as T
            }
            else -> {
                throw ColorTypeException("Color type not supported")
            }
        }
    }

    override fun toString(): String {
        return "RGBColor(red=$red, green=$green, blue=$blue, alpha=$alpha)"
    }

    override fun rangeTo(other: Color): ColorInterpolation<RGBColor> {
        return ColorInterpolation(this, other.toSpace(ColorType.RGB))
    }

    override fun equals(other: Any?): Boolean {
        if (other !is Color) return false

        val otherColor = other.toSpace(ColorType.RGB)
        return red == otherColor.red && green == otherColor.green && blue == otherColor.blue && alpha == otherColor.alpha
    }

    override fun toCSSString(colorFormat: RGBFormats, includeAlpha: Boolean): String {
        when (colorFormat) {
            RGBFormats.HEX -> {
                val redHex = (red * 255).toInt().toString(16).padStart(2, '0')
                val greenHex = (green * 255).toInt().toString(16).padStart(2, '0')
                val blueHex = (blue * 255).toInt().toString(16).padStart(2, '0')
                val alphaHex = (alpha * 255).toInt().toString(16).padStart(2, '0')

                if (redHex[0] == redHex[1] && greenHex[0] == greenHex[1] && blueHex[0] == blueHex[1]) {
                    if (includeAlpha && alphaHex[0] == alphaHex[1]) return "#${redHex[0]}${greenHex[0]}${blueHex[0]}${alphaHex[0]}"
                    else if (!includeAlpha) return "#${redHex[0]}${greenHex[0]}${blueHex[0]}"
                }

                return if (includeAlpha) "#$redHex$greenHex$blueHex$alphaHex" else "#$redHex$greenHex$blueHex"
            }

            RGBFormats.LONG_HEX -> {
                val redHex = (red * 255).toInt().toString(16).padStart(2, '0')
                val greenHex = (green * 255).toInt().toString(16).padStart(2, '0')
                val blueHex = (blue * 255).toInt().toString(16).padStart(2, '0')
                val alphaHex = (alpha * 255).toInt().toString(16).padStart(2, '0')

                return if (includeAlpha) "#$redHex$greenHex$blueHex$alphaHex" else "#$redHex$greenHex$blueHex"
            }

            RGBFormats.DECIMAL -> {
                val redDec = (red * 255).toInt()
                val greenDec = (green * 255).toInt()
                val blueDec = (blue * 255).toInt()

                return if (includeAlpha) "rgb($redDec $greenDec $blueDec / %.2f)".format(alpha) else "rgb($redDec $greenDec $blueDec)"
            }

            RGBFormats.PERCENT -> {
                val redPercent = red * 100
                val greenPercent = green * 100
                val bluePercent = blue * 100

                return if (includeAlpha) {
                    "%.2f%% %.2f%% %.2f%% / %.2f".format(redPercent, greenPercent, bluePercent, alpha)
                } else {
                    "%.2f%% %.2f%% %.2f%%".format(redPercent, greenPercent, bluePercent)
                }
            }
        }
    }

    override fun hashCode(): Int {
        var result = red.hashCode()
        result = 31 * result + green.hashCode()
        result = 31 * result + blue.hashCode()
        result = 31 * result + alpha.hashCode()
        return result
    }
}