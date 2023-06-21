package app.moreo.ucl.colors

import app.moreo.ucl.Color
import app.moreo.ucl.enums.ColorType
import app.moreo.ucl.exceptions.ColorTypeException
import app.moreo.ucl.utils.precisionEquals
import java.math.RoundingMode
import kotlin.math.abs
import kotlin.math.pow

class XYZD65Color(var x: Float, var y: Float, var z: Float, override var alpha: Float = 1f): Color {

    companion object {
        @JvmField
        val type: ColorType<XYZD65Color> = ColorType.XYZ_D65
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : Color> toSpace(color: ColorType<T>): T {
        return when(color) {
            ColorType.XYZ_D65 -> this as T
            ColorType.RGB -> {
                val red = adj((3.2404542f * x) + (-1.5371385f * y) + (-0.4985314f * z)).toBigDecimal().setScale(4, RoundingMode.HALF_EVEN).toFloat()
                val green = adj((-0.9692660f * x) + (1.8760108f * y) + (0.0415560f * z)).toBigDecimal().setScale(4, RoundingMode.HALF_EVEN).toFloat()
                val blue = adj((0.0556434f * x) + (-0.2040259f * y) + (1.0572252f * z)).toBigDecimal().setScale(4, RoundingMode.HALF_EVEN).toFloat()

                val fixedRed = if (red < 0.001f) 0f else if (red > 1) 1f else red
                val fixedGreen = if (green < 0.001f) 0f else if (green > 1) 1f else green
                val fixedBlue = if (blue < 0.001f) 0f else if (blue > 1) 1f else blue

                return RGBColor(fixedRed, fixedGreen, fixedBlue, alpha) as T
            }
            ColorType.HSV, ColorType.HSB, ColorType.HSL -> {
                return toSpace(ColorType.RGB).toSpace(color)
            }
            else -> throw ColorTypeException("Cannot convert to $color")
        }
    }

    private fun adj(c: Float): Float {
        if (abs(c) < 0.0031308) {
            return 12.92f * c;
        }
        return 1.055f * c.pow(0.41666f) - 0.055f;
    }

    override fun equals(other: Any?): Boolean {
        if (other !is Color) return false
        val otherXYZ = other.toSpace(type)
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
}