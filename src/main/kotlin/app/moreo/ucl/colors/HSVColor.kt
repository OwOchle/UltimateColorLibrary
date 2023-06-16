package app.moreo.ucl.colors

import app.moreo.ucl.Color
import app.moreo.ucl.ColorInterpolation
import app.moreo.ucl.enums.ColorType
import app.moreo.ucl.exceptions.ColorTypeException
import app.moreo.ucl.interfaces.Interpolatable

/**
 * HSV color representation
 * HSV is equivalent to HSB
 * @property hue hue in radians
 * @property saturation saturation between 0 and 1
 * @property value value between 0 and 1
 *
 * @param hue hue in radians
 * @param saturation saturation between 0 and 1
 * @param value value between 0 and 1
 *
 *
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
     *
     * @param hue hue in degrees
     * @param saturation saturation between 0 and 100
     * @param value value between 0 and 100
     *
     * Note: HSV = HSB
     */
    constructor(hue: Int, saturation: Int, value: Int, alpha: Float = 1f): this(Math.toRadians(hue.toDouble()).toFloat(), saturation / 100f, value / 100f, alpha)

    @Suppress("UNCHECKED_CAST")
    override fun <T : Color> toColor(color: ColorType<T>): T {
        return when (color) {
            ColorType.HSV -> {
                this as T
            }
            ColorType.HSL -> {
                TODO("HSV to HSL")
            }
            ColorType.RGB -> {
                TODO("HSV to RGB")
            }
            ColorType.HSB -> {
                this as T
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
        return ColorInterpolation(this, other.toColor(ColorType.HSV))
    }
}