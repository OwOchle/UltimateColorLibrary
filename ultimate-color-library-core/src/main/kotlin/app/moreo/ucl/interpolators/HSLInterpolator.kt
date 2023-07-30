package app.moreo.ucl.interpolators

import app.moreo.ucl.colors.HSLColor
import app.moreo.ucl.enums.InterpolationPath
import app.moreo.ucl.interfaces.ColorInterpolator
import app.moreo.ucl.utils.NumberInterpolator
import app.moreo.ucl.utils.TWO_PI
import app.moreo.ucl.utils.interpolate
import kotlin.math.abs

/**
 * HSL space color interpolator
 * @property start the start color
 * @property end the end color
 * @property steps the number of steps to interpolate
 * @property path the [InterpolationPath] to use
 * @property numberInterpolator the number interpolator to use
 */
class HSLInterpolator(override val start: HSLColor, override val end: HSLColor, override val steps: Int,
                      override val path: InterpolationPath, override val numberInterpolator: NumberInterpolator = ::interpolate):
    ColorInterpolator<HSLColor> {
    private var currentStep = 0
    private var offset = 0f

    init {
        offset = if (path == InterpolationPath.LONGEST) {
            if (abs(start.hue - end.hue) > abs(start.hue - (end.hue + TWO_PI))) {
                0f
            } else {
                TWO_PI
            }
        } else {
            if (abs(start.hue - end.hue) > abs(start.hue - (end.hue + TWO_PI))) {
                TWO_PI
            } else {
                0f
            }
        }
    }

    override fun hasNext(): Boolean {
        return currentStep < steps
    }

    override fun next(): HSLColor {
        val time = currentStep.toFloat() / (steps - 1)

        val offset = when (path) {
            InterpolationPath.SHORTEST -> 0f
            InterpolationPath.LONGEST -> TWO_PI
        }

        val hue = numberInterpolator(start.hue, end.hue + offset, time)
        val saturation = numberInterpolator(start.saturation, end.saturation, time)
        val lightness = numberInterpolator(start.lightness, end.lightness, time)
        val alpha = numberInterpolator(start.alpha, end.alpha, time)
        currentStep++

        return HSLColor(hue, saturation, lightness, alpha)
    }

}