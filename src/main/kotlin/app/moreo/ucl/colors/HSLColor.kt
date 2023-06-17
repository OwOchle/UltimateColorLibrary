package app.moreo.ucl.colors

import app.moreo.ucl.Color
import app.moreo.ucl.ColorInterpolation
import app.moreo.ucl.enums.ColorType
import app.moreo.ucl.exceptions.ColorTypeException
import app.moreo.ucl.interfaces.Interpolatable
import app.moreo.ucl.utils.TWO_PI
import kotlin.math.abs


/**
 * HSL color representation
 * @property hue hue in radians
 * @property saturation saturation between 0 and 1
 * @property lightness lightness between 0 and 1
 * @property alpha alpha between 0 and 1
 *
 * @param hue hue in radians
 * @param saturation saturation between 0 and 1
 * @param lightness lightness between 0 and 1
 * @param alpha alpha between 0 and 1
 */
class HSLColor(hue: Float, var saturation: Float, var lightness: Float, override var alpha: Float = 1f): Color, Interpolatable<HSLColor> {

    var hue: Float = hue.mod(TWO_PI)

    var degreesHue: Int
        inline get() = Math.toDegrees(hue.toDouble()).toInt()
        inline set(value) {
            hue = Math.toRadians(value.toDouble()).toFloat()
        }

    /**
     * HSL color representation
     * @property hue hue in radians
     * @property saturation saturation between 0 and 1
     * @property lightness lightness between 0 and 1
     * @property alpha alpha between 0 and 1
     *
     * @param hue hue in degrees
     * @param saturation saturation between 0 and 100
     * @param lightness lightness between 0 and 100
     * @param alpha alpha between 0 and 1
     */
    constructor(hue: Int, saturation: Int, lightness: Int, alpha: Float = 1f): this(Math.toRadians(hue.toDouble().mod(360f)).toFloat(), saturation / 100f, lightness / 100f, alpha)

    @Suppress("UNCHECKED_CAST")
    override fun <T : Color> toSpace(color: ColorType<T>): T {
        return when (color) {
            ColorType.HSV, ColorType.HSB -> {
                val value = lightness + saturation * minOf(lightness, 1 - lightness)
                val saturation = if (value == 0f) 0f else 2 * (1 - (lightness / value))
                return HSVColor(hue, saturation, value) as T
            }
            ColorType.HSL -> {
                this as T
            }
            ColorType.RGB -> {
                val c = (1 - abs(2 * lightness - 1)) * saturation
                val hPrime = degreesHue / 60f
                val x = c * (1 - abs(hPrime.mod(2f) - 1))

                val (r1, g1, b1) = when {
                    hPrime < 1 -> Triple(c, x, 0f)
                    hPrime < 2 -> Triple(x, c, 0f)
                    hPrime < 3 -> Triple(0f, c, x)
                    hPrime < 4 -> Triple(0f, x, c)
                    hPrime < 5 -> Triple(x, 0f, c)
                    else -> Triple(c, 0f, x)
                }

                val m = lightness - c / 2f

                return RGBColor(r1 + m, g1 + m, b1 + m) as T
            }
            else -> {
                throw ColorTypeException("Color type not supported")
            }
        }
    }

    override fun toString(): String {
        return "HSLColor(hue=$hue, saturation=$saturation, lightness=$lightness, alpha=$alpha)"
    }

    override fun rangeTo(other: Color): ColorInterpolation<HSLColor> {
        return ColorInterpolation(this, other)
    }
}