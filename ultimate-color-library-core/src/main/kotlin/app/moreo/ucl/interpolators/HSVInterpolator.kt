package app.moreo.ucl.interpolators

import app.moreo.ucl.colors.HSVColor
import app.moreo.ucl.enums.InterpolationPath
import app.moreo.ucl.interfaces.ColorInterpolator
import app.moreo.ucl.utils.NumberInterpolator
import app.moreo.ucl.utils.TWO_PI
import app.moreo.ucl.utils.interpolate
import kotlin.math.abs

/**
 * HSV space color interpolator
 * @property start the start color
 * @property end the end color
 * @property steps the number of steps to interpolate
 * @property path the [InterpolationPath] to use
 * @property numberInterpolator the number interpolator to use
 */
class HSVInterpolator(override val start: HSVColor, override val end: HSVColor, override val steps: Int,
                      override val path: InterpolationPath, override val numberInterpolator: NumberInterpolator = ::interpolate):
    ColorInterpolator<HSVColor> {
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

    override fun next(): HSVColor {
        val time = currentStep.toFloat() / (steps - 1)


        val hue = numberInterpolator(start.hue, end.hue + offset, time)
        val saturation = numberInterpolator(start.saturation, end.saturation, time)
        val value = numberInterpolator(start.value, end.value, time)
        val alpha = numberInterpolator(start.alpha, end.alpha, time)
        currentStep++

        return HSVColor(hue, saturation, value, alpha)
    }
}