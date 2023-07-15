package app.moreo.ucl.colors

import app.moreo.ucl.Color
import app.moreo.ucl.ColorInterpolation
import app.moreo.ucl.enums.ColorType
import app.moreo.ucl.exceptions.ColorConversionException
import app.moreo.ucl.interfaces.Interpolatable
import app.moreo.ucl.utils.LAB_DELTA
import app.moreo.ucl.utils.precisionEquals
import kotlin.math.pow

/**
 * CIE LAB color representation. <br>
 * <b>NOTE:</b> lightness, a* and b* are not clamped but once a certain value, it will just be white.
 * @property lightness lightness (L*)
 * @property aStar a* (A)
 * @property bStar b* (B)
 * @property alpha alpha between 0 and 1
 *
 * @param lightness lightness (L*)
 * @param aStar a* (A)
 * @param bStar b* (B)
 * @param alpha alpha between 0 and 1
 *
 * @see <a href="https://en.wikipedia.org/wiki/CIELAB_color_space">CIE L*a*b* color space</a> for more infos.
 */
class LabColor @JvmOverloads constructor(var lightness: Float, var aStar: Float, var bStar: Float, override var alpha: Float = 1f): Interpolatable<LabColor> {

    companion object {
        @JvmField
        val TYPE = ColorType.LAB
    }

    /**
     * Compute the f^-1(t) function for CIE LAB to CIE XYZ conversion.
     */
    private fun fInverse(t: Float): Float {
        if (t > LAB_DELTA) {
            return t.pow(3f)
        }

        return 3*(LAB_DELTA.pow(2f)) * (t - 4f/29f)
    }

    override fun rangeTo(other: Color): ColorInterpolation<LabColor> {
        TODO("Not yet implemented")
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : Color> toSpace(color: ColorType<T>): T {
        return when (color) {
            ColorType.LAB -> this as T
            ColorType.XYZ_D65 -> {
                val x = XYZD65Color.D65_REFERENCE_WHITE.x * fInverse((lightness + 16f) / 116f + aStar / 500f)
                val y = XYZD65Color.D65_REFERENCE_WHITE.y * fInverse((lightness + 16f) / 116f)
                val z = XYZD65Color.D65_REFERENCE_WHITE.z * fInverse((lightness + 16f) / 116f - bStar / 200f)

                XYZD65Color(x, y, z, alpha) as T
            }
            ColorType.SRGB, ColorType.HSV, ColorType.HSL, ColorType.HSB -> {
                toSpace(ColorType.XYZ_D65).toSpace(color)
            }

            else -> throw ColorConversionException("Color conversion not supported from L*a*b* to $color")
        }
    }

    override fun equals(other: Any?): Boolean {
        if (other !is Color) return false

        val otherLab = other.toSpace(ColorType.LAB)

        return lightness.precisionEquals(otherLab.lightness) && aStar.precisionEquals(otherLab.aStar) && bStar.precisionEquals(otherLab.bStar) && alpha.precisionEquals(otherLab.alpha)
    }

    override fun toString(): String {
        return "LabColor(lightness=$lightness, aStar=$aStar, bStar=$bStar, alpha=$alpha)"
    }

    override fun hashCode(): Int {
        var result = lightness.hashCode()
        result = 31 * result + aStar.hashCode()
        result = 31 * result + bStar.hashCode()
        result = 31 * result + alpha.hashCode()
        return result
    }
}