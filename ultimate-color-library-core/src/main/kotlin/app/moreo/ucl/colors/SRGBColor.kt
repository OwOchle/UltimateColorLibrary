package app.moreo.ucl.colors

import app.moreo.ucl.Color
import app.moreo.ucl.ColorInterpolation
import app.moreo.ucl.enums.ColorType
import app.moreo.ucl.exceptions.ColorConversionException
import app.moreo.ucl.exceptions.GamutException
import app.moreo.ucl.interfaces.Interpolatable
import app.moreo.ucl.utils.precisionEquals
import app.moreo.ucl.utils.toRadians
import kotlin.math.abs
import kotlin.math.pow

/**
 * sRGB color representation
 * @property red red between 0 and 1
 * @property green green between 0 and 1
 * @property blue blue between 0 and 1
 *
 * @param red red between 0 and 1
 * @param green green between 0 and 1
 * @param blue blue between 0 and 1
 * @param alpha alpha between 0 and 1
 */
class SRGBColor @JvmOverloads constructor(var red: Float, var green: Float, var blue: Float, override var alpha: Float = 1f): Interpolatable<SRGBColor> {

    companion object {
        @JvmField
        val TYPE = ColorType.SRGB
    }

    /**
     * sRGB color representation
     * @property red red between 0 and 1
     * @property green green between 0 and 1
     * @property blue blue between 0 and 1
     *
     * @param red red between 0 and 255
     * @param green green between 0 and 255
     * @param blue blue between 0 and 255
     * @param alpha alpha between 0 and 1
     */
    @JvmOverloads constructor(red: Short, green: Short, blue: Short, alpha: Float = 1f) : this(red / 255f, green / 255f, blue / 255f, alpha) {
        if (minOf(red, green, blue) < 0 || maxOf(red, green, blue) > 255) throw GamutException("SRGB values must be between 0 and 255")
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
            ColorType.SRGB -> {
                this as T
            }

            ColorType.XYZ_D65 -> {
                val linearRed = if (red <= 0.04045f) red / 12.92f else ((red + 0.055f) / 1.055f).pow(2.4f)
                val linearGreen = if (green <= 0.04045f) green / 12.92f else ((green + 0.055f) / 1.055f).pow(2.4f)
                val linearBlue = if (blue <= 0.04045f) blue / 12.92f else ((blue + 0.055f) / 1.055f).pow(2.4f)

                return XYZD65Color(
                    0.4124f * linearRed + 0.3576f * linearGreen + 0.1805f * linearBlue,
                    0.2126f * linearRed + 0.7152f * linearGreen + 0.0722f * linearBlue,
                    0.0193f * linearRed + 0.1192f * linearGreen + 0.9505f * linearBlue
                ) as T
            }

            ColorType.LAB -> {
                return toSpace(ColorType.XYZ_D65).toSpace(ColorType.LAB)
            }

            else -> {
                throw ColorConversionException("Color conversion not supported from sRGB to $color")
            }
        }
    }

    override fun toString(): String {
        return "RGBColor(red=$red, green=$green, blue=$blue, alpha=$alpha)"
    }

    override fun rangeTo(other: Color): ColorInterpolation<SRGBColor> {
        return ColorInterpolation(this, other.toSpace(ColorType.SRGB))
    }

    override fun equals(other: Any?): Boolean {
        if (other !is Color) return false

        val otherSRGB = other.toSpace(ColorType.SRGB)
        return red.precisionEquals(otherSRGB.red) && green.precisionEquals(otherSRGB.green) && blue.precisionEquals(otherSRGB.blue) && alpha.precisionEquals(otherSRGB.alpha)
    }

    fun toInt(): Int {
        return (red * 255).toInt() shl 16 or ((green * 255).toInt() shl 8) or (blue * 255).toInt()
    }

    override fun hashCode(): Int {
        var result = red.hashCode()
        result = 31 * result + green.hashCode()
        result = 31 * result + blue.hashCode()
        result = 31 * result + alpha.hashCode()
        return result
    }
}