package app.moreo.ucl.colors

import app.moreo.ucl.Color
import app.moreo.ucl.ColorInterpolation
import app.moreo.ucl.enums.ColorType
import app.moreo.ucl.exceptions.ColorTypeException
import app.moreo.ucl.exceptions.GamutException
import app.moreo.ucl.interfaces.Interpolatable
import app.moreo.ucl.utils.toRadians
import kotlin.math.abs

class RGBColor(var red: Float, var green: Float, var blue: Float, override var alpha: Float = 1f): Color, Interpolatable<RGBColor> {

    constructor(red: Short, green: Short, blue: Short, alpha: Float = 1f) : this(red / 255f, green / 255f, blue / 255f, alpha) {
        if (minOf(red, green, blue) < 0 || maxOf(red, green, blue) > 255) throw GamutException("RGB varues must be between 0 and 255")
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : Color> toColor(color: ColorType<T>): T {
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

                println("Hue: $hue")

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
        return ColorInterpolation(this, other.toColor(ColorType.RGB))
    }
}