package app.moreo.ucl.colors

import app.moreo.ucl.Color
import app.moreo.ucl.enums.ColorType
import app.moreo.ucl.exceptions.ColorConversionException
import app.moreo.ucl.interfaces.Copyable
import app.moreo.ucl.utils.LAB_DELTA
import app.moreo.ucl.utils.correct
import app.moreo.ucl.utils.precisionEquals
import java.math.RoundingMode
import kotlin.math.pow

class XYZD65Color @JvmOverloads constructor(var x: Float, var y: Float, var z: Float, override var alpha: Float = 1f): Color, Copyable<XYZD65Color> {

    companion object {
        @JvmField
        val TYPE: ColorType<XYZD65Color> = ColorType.XYZ_D65

        @JvmField
        val D65_REFERENCE_WHITE = XYZD65Color(0.95047f, 1f, 1.08883f)
    }

    /**
     *
     */
    private fun f(t: Float): Float {
        if (t > LAB_DELTA.pow(3)) {
            return t.pow(1f/3f)
        }

        return (t / (3 * LAB_DELTA.pow(2))) + (4f/29f)
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : Color> toSpace(color: ColorType<T>): T {
        return when(color) {
            ColorType.XYZ_D65 -> this as T
            ColorType.SRGB -> {
                val red = (3.2404542f * x) + (-1.5371385f * y) + (-0.4985314f * z)
                val green = (-0.9692660f * x) + (1.8760108f * y) + (0.0415560f * z)
                val blue = (0.0556434f * x) + (-0.2040259f * y) + (1.0572252f * z)

                val compandingRed = if (red > 0.0031308) {
                    1.055f * red.pow(0.41666f) - 0.055f
                } else {
                    12.92f * red
                }

                val compandingGreen = if (green > 0.0031308) {
                    1.055f * green.pow(0.41666f) - 0.055f
                } else {
                    12.92f * green
                }

                val compandingBlue = if (blue > 0.0031308) {
                    1.055f * blue.pow(0.41666f) - 0.055f
                } else {
                    12.92f * blue
                }

                val correctedRed = compandingRed.toBigDecimal().setScale(4, RoundingMode.HALF_EVEN).toFloat()
                val correctedGreen = compandingGreen.toBigDecimal().setScale(4, RoundingMode.HALF_EVEN).toFloat()
                val correctedBlue = compandingBlue.toBigDecimal().setScale(4, RoundingMode.HALF_EVEN).toFloat()

                SRGBColor(correctedRed.correct(), correctedGreen.correct(), correctedBlue.correct(), alpha) as T
            }
            ColorType.HSV, ColorType.HSB, ColorType.HSL -> {
                toSpace(ColorType.SRGB).toSpace(color)
            }

            ColorType.LAB -> {
                val l = 116f * f(y / D65_REFERENCE_WHITE.y) - 16f
                val a = 500f * (f(x / D65_REFERENCE_WHITE.x) - f(y / D65_REFERENCE_WHITE.y))
                val b = 200f * (f(y / D65_REFERENCE_WHITE.y) - f(z / D65_REFERENCE_WHITE.z))

                LabColor(l, a, b, alpha) as T
            }
            else -> throw ColorConversionException("Color conversion not supported from XYZD65 to $color")
        }
    }

    override fun equals(other: Any?): Boolean {
        if (other !is Color) return false
        val otherXYZ = other.toSpace(TYPE)
        return x.precisionEquals(otherXYZ.x) && y.precisionEquals(otherXYZ.y) && z.precisionEquals(otherXYZ.z) && alpha.precisionEquals(otherXYZ.alpha)
    }

    override fun toString(): String {
        return "XYZColor(x=$x, y=$y, z=$z, alpha=$alpha)"
    }

    override fun hashCode(): Int {
        var result = x.hashCode()
        result = 31 * result + y.hashCode()
        result = 31 * result + z.hashCode()
        result = 31 * result + alpha.hashCode()
        return result
    }

    override fun copy(): XYZD65Color {
        return XYZD65Color(x, y, z, alpha)
    }
}