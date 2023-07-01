package app.moreo.ucl

import app.moreo.ucl.colors.HSLColor
import app.moreo.ucl.colors.HSVColor
import app.moreo.ucl.colors.SRGBColor
import app.moreo.ucl.enums.ColorType
import app.moreo.ucl.enums.InterpolationPath
import app.moreo.ucl.exceptions.ColorTypeException
import app.moreo.ucl.exceptions.InterpolationException
import app.moreo.ucl.interfaces.ColorInterpolator
import app.moreo.ucl.interpolators.HSLInterpolator
import app.moreo.ucl.interpolators.HSVInterpolator
import app.moreo.ucl.interpolators.RGBInterpolator
import app.moreo.ucl.utils.interpolate

/**
 * An iterable representing a color interpolation between two colors
 * @property start the start color
 * @property end the end color
 * @property space the color space to interpolate in
 * @property steps the number of steps to interpolate
 * @property numberInterpolator the number interpolator to use
 * @property path the [InterpolationPath] to use
 */
class ColorInterpolation<T: Color> (val start: Color, val end: Color): Iterable<T> {
    private var space: ColorType<out Color> = when (start) {
        is SRGBColor -> ColorType.SRGB
        is HSVColor -> ColorType.HSV
        is HSLColor -> ColorType.HSL
        else -> throw ColorTypeException("Color type not supported")
    }
        set(value) {
            if (field != value) {
                actualStart = start.toSpace(value)
                actualEnd = end.toSpace(value)
                field = value
            }
        }

    private var steps = Color.DEFAULT_COLOR_INTERPOLATOR_STEPS

    private var actualStart = start
    private var actualEnd = end.toSpace(space)

    private var numberInterpolator: (Float, Float, Float) -> Float = ::interpolate

    private var path: InterpolationPath = InterpolationPath.SHORTEST


    @Suppress("UNCHECKED_CAST")
    infix fun <N: Color> space(colorType: ColorType<N>): ColorInterpolation<N> {
        this.space = colorType
        return this as ColorInterpolation<N>
    }

    infix fun steps(steps: Int): ColorInterpolation<T> {
        this.steps = steps
        return this
    }

    infix fun numberInterpolator(numberInterpolator: (Float, Float, Float) -> Float): ColorInterpolation<T> {
        this.numberInterpolator = numberInterpolator
        return this
    }

    infix fun path(path: InterpolationPath): ColorInterpolation<T> {
        this.path = path
        return this
    }

    @Suppress("UNCHECKED_CAST")
    override fun iterator(): ColorInterpolator<T> {
        return when (space) {
            ColorType.SRGB -> RGBInterpolator(actualStart as SRGBColor, actualEnd as SRGBColor, steps, path,  numberInterpolator) as ColorInterpolator<T>
            ColorType.HSV -> HSVInterpolator(actualStart as HSVColor, actualEnd as HSVColor, steps, path,  numberInterpolator) as ColorInterpolator<T>
            ColorType.HSB -> HSVInterpolator(actualStart as HSVColor, actualEnd as HSVColor, steps, path,  numberInterpolator) as ColorInterpolator<T>
            ColorType.HSL -> HSLInterpolator(actualStart as HSLColor, actualEnd as HSLColor, steps, path, numberInterpolator) as ColorInterpolator<T>
            else -> throw InterpolationException("Color type not supported")
        }
    }
}