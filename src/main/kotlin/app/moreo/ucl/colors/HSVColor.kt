package app.moreo.ucl.colors

import app.moreo.ucl.Color
import app.moreo.ucl.ColorInterpolation
import app.moreo.ucl.enums.ColorType
import app.moreo.ucl.exceptions.ColorTypeException
import app.moreo.ucl.interfaces.Interpolatable
import kotlin.math.abs

/**
 * HSV color representation
 * HSV is equivalent to HSB
 * @property hue hue in radians
 * @property saturation saturation between 0 and 1
 * @property value value between 0 and 1
 * @property alpha alpha between 0 and 1
 *
 * @param hue hue in radians
 * @param saturation saturation between 0 and 1
 * @param value value between 0 and 1
 * @param alpha alpha between 0 and 1
 */
class HSVColor(var hue: Float, var saturation: Float, var value: Float, override var alpha: Float = 1f): Color, Interpolatable<HSVColor> {

    var degreesHue: Float
        inline get() = Math.toRadians(hue.toDouble()).toFloat()
        inline set(value) {
            hue = Math.toRadians(value.toDouble()).toFloat()
        }

    /**
     * HSV color representation
     * HSV is equivalent to HSB
     * @property hue hue in radians
     * @property saturation saturation between 0 and 1
     * @property value value between 0 and 1
     * @property alpha alpha between 0 and 1
     *
     * @param hue hue in degrees
     * @param saturation saturation between 0 and 100
     * @param value value between 0 and 100
     * @param alpha alpha between 0 and 1
     */
    constructor(hue: Int, saturation: Int, value: Int, alpha: Float = 1f): this(Math.toRadians(hue.toDouble()).toFloat(), saturation / 100f, value / 100f, alpha)

    @Deprecated("Use toSpace instead", replaceWith = ReplaceWith("toSpace(color)"))
    override fun <T : Color> toColor(color: ColorType<T>): T = toSpace(color)

    @Suppress("UNCHECKED_CAST")
    override fun <T : Color> toSpace(color: ColorType<T>): T {
        return when (color) {
            ColorType.HSV, ColorType.HSB -> {
                this as T
            }
            ColorType.HSL -> {
                val lightness = value * (1 - (saturation / 2))
                val saturation = if (lightness == 0f || lightness == 1f) 0f else (value - lightness) / minOf(lightness, 1 - lightness)
                return HSLColor(hue, saturation, lightness) as T
            }
            ColorType.RGB -> {
                val c = value * saturation
                val hPrime = degreesHue / 60f
                val x = c * (1 - abs(hPrime.mod(2f) - 1))

                val (r1, g1, b1) = when {
                    hPrime < 1 -> Triple(c, x, 0f)
                    hPrime < 2 -> Triple(x, c, 0f)
                    hPrime < 3 -> Triple(0f, c, x)
                    hPrime < 4 -> Triple(0f, x, c)
                    hPrime < 5 -> Triple(x, 0f, c)
                    hPrime < 6 -> Triple(c, 0f, x)
                    else -> Triple(0f, 0f, 0f)
                }

                val m = value - c

                return RGBColor(r1 + m, g1 + m, b1 + m, alpha) as T
            }
            else -> {
                throw ColorTypeException("Color type not supported")
            }
        }
    }

    override fun toString(): String {
        return "HSVColor(hue=$hue, saturation=$saturation, value=$value, alpha=$alpha)"
    }

    override fun rangeTo(other: Color): ColorInterpolation<HSVColor> {
        return ColorInterpolation(this, other.toSpace(ColorType.HSV))
    }
}