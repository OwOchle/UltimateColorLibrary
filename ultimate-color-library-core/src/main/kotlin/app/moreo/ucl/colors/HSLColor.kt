package app.moreo.ucl.colors

import app.moreo.ucl.Color
import app.moreo.ucl.ColorInterpolation
import app.moreo.ucl.enums.ColorType
import app.moreo.ucl.exceptions.ColorConversionException
import app.moreo.ucl.interfaces.Interpolatable
import app.moreo.ucl.utils.BoundedFloat
import app.moreo.ucl.utils.TWO_PI
import app.moreo.ucl.utils.precisionEquals
import kotlin.math.abs
import kotlin.math.round


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
class HSLColor @JvmOverloads constructor(hue: Float, saturation: Float, lightness: Float, alpha: Float = 1f): Interpolatable<HSLColor> {

    companion object {
        @JvmField
        val TYPE = ColorType.HSL
    }

    var hue by BoundedFloat(hue, 0f, TWO_PI)

    var degreesHue: Int
        inline get() = round(Math.toDegrees(hue.toDouble())).toInt()
        inline set(value) {
            hue = Math.toRadians(value.toDouble()).toFloat()
        }

    var saturation by BoundedFloat(saturation, 0f, 1f)

    var lightness by BoundedFloat(lightness, 0f, 1f)

    override var alpha by BoundedFloat(alpha, 0f, 1f)

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
    @JvmOverloads constructor(hue: Int, saturation: Int, lightness: Int, alpha: Float = 1f): this(Math.toRadians(hue.toDouble().mod(360f)).toFloat(), saturation / 100f, lightness / 100f, alpha)

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
            ColorType.SRGB -> {
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

                return SRGBColor(r1 + m, g1 + m, b1 + m) as T
            }
            ColorType.XYZ_D65 -> {
                return toSpace(ColorType.SRGB).toSpace(ColorType.XYZ_D65)
            }

            ColorType.LAB -> {
                return toSpace(ColorType.XYZ_D65).toSpace(ColorType.LAB)
            }

            else -> {
                throw ColorConversionException("Color conversion not supported from HSL to $color")
            }
        }
    }

    override fun toString(): String {
        return "HSLColor(hue=$hue, saturation=$saturation, lightness=$lightness, alpha=$alpha)"
    }

    override fun rangeTo(other: Color): ColorInterpolation<HSLColor> {
        return ColorInterpolation(this, other)
    }

    override fun equals(other: Any?): Boolean {
        if (other !is Color) return false

        val otherHSL = other.toSpace(ColorType.HSL)
        return hue.precisionEquals(otherHSL.hue) && saturation.precisionEquals(otherHSL.saturation) && lightness.precisionEquals(otherHSL.lightness) && alpha.precisionEquals(otherHSL.alpha)
    }

    override fun hashCode(): Int {
        var result = hue.hashCode()
        result = 31 * result + saturation.hashCode()
        result = 31 * result + lightness.hashCode()
        result = 31 * result + alpha.hashCode()
        return result
    }
}