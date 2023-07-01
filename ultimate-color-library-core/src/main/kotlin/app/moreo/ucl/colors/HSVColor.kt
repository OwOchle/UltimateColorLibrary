package app.moreo.ucl.colors

import app.moreo.ucl.Color
import app.moreo.ucl.ColorInterpolation
import app.moreo.ucl.enums.ColorType
import app.moreo.ucl.exceptions.ColorTypeException
import app.moreo.ucl.interfaces.Interpolatable
import app.moreo.ucl.utils.TWO_PI
import kotlin.math.abs
import kotlin.math.round

/**
 * HSV color representation
 * HSV is equivalent to HSB
 * @property hue hue in radians, between 0 and two pi
 * @property saturation saturation between 0 and 1
 * @property value value between 0 and 1
 * @property alpha alpha between 0 and 1
 *
 * @param hue hue in radians, (modulo two pi gets applied, so you may lose data in conversion)
 * @param saturation saturation between 0 and 1
 * @param value value between 0 and 1
 * @param alpha alpha between 0 and 1
 */
class HSVColor @JvmOverloads constructor(hue: Float, var saturation: Float, var value: Float, override var alpha: Float = 1f): Color, Interpolatable<HSVColor> {

    companion object {
        @JvmField
        val TYPE = ColorType.HSV
    }

    var hue: Float = hue.mod(TWO_PI)

    var degreesHue: Int
        inline get() = round(Math.toDegrees(hue.toDouble())).toInt()
        inline set(value) {
            hue = Math.toRadians(value.toDouble()).toFloat()
        }

    /**
     * HSV color representation
     * HSV is equivalent to HSB
     * @property hue hue in radians, between 0 and two pi
     * @property saturation saturation between 0 and 1
     * @property value value between 0 and 1
     * @property alpha alpha between 0 and 1
     *
     * @param hue hue in degrees, (modulo 360 gets applied, so you may lose data in conversion)
     * @param saturation saturation between 0 and 100
     * @param value value between 0 and 100
     * @param alpha alpha between 0 and 1
     */
    @JvmOverloads constructor(hue: Int, saturation: Int, value: Int, alpha: Float = 1f): this(Math.toRadians(hue.toDouble()).toFloat(), saturation / 100f, value / 100f, alpha)

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
            ColorType.SRGB -> {
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

                return SRGBColor(r1 + m, g1 + m, b1 + m, alpha) as T
            }

            ColorType.XYZ_D65 -> {
                return this.toSpace(ColorType.SRGB).toSpace(ColorType.XYZ_D65)
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

    override fun equals(other: Any?): Boolean {
        if (other !is Color) return false

        val otherHSV = other.toSpace(ColorType.HSV)
        return hue == otherHSV.hue && saturation == otherHSV.saturation && value == otherHSV.value && alpha == otherHSV.alpha
    }

    override fun hashCode(): Int {
        var result = hue.hashCode()
        result = 31 * result + saturation.hashCode()
        result = 31 * result + value.hashCode()
        result = 31 * result + alpha.hashCode()
        return result
    }
}