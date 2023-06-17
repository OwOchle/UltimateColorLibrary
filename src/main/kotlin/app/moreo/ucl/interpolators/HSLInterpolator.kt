package app.moreo.ucl.interpolators

import app.moreo.ucl.colors.HSLColor
import app.moreo.ucl.enums.InterpolationPath
import app.moreo.ucl.interfaces.ColorInterpolator
import app.moreo.ucl.utils.TWO_PI
import app.moreo.ucl.utils.interpolate

class HSLInterpolator(override val start: HSLColor, override val end: HSLColor, override val steps: Int,
                      override val path: InterpolationPath, override val numberInterpolator: (Float, Float, Float) -> Float = ::interpolate):
    ColorInterpolator<HSLColor> {
    private var currentStep = 0

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