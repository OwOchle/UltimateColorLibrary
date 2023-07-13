package app.moreo.ucl.interpolators

import app.moreo.ucl.colors.SRGBColor
import app.moreo.ucl.enums.InterpolationPath
import app.moreo.ucl.interfaces.ColorInterpolator
import app.moreo.ucl.utils.NumberInterpolator
import app.moreo.ucl.utils.interpolate
import java.util.logging.Logger

/**
 * SRGB space color interpolator
 * @property start the start color
 * @property end the end color
 * @property steps the number of steps to interpolate
 * @property path the [InterpolationPath] to use
 * @property numberInterpolator the number interpolator to use
 */
class RGBInterpolator(override val start: SRGBColor, override val end: SRGBColor, override val steps: Int,
                      override val path: InterpolationPath, override val numberInterpolator: NumberInterpolator = ::interpolate):
    ColorInterpolator<SRGBColor> {

    init {
        if (path != InterpolationPath.SHORTEST) Logger.getLogger("UltimateColorLibrary").warning("SRGB interpolation only supports SHORTEST interpolation path. Ignoring interpolation path.")
    }

    private var currentStep = 0

    override fun hasNext(): Boolean {
        return currentStep < steps
    }

    override fun next(): SRGBColor {
        val time = currentStep.toFloat() / (steps - 1)

        val r = numberInterpolator(start.red, end.red, time)
        val g = numberInterpolator(start.green, end.green, time)
        val b = numberInterpolator(start.blue, end.blue, time)
        val alpha = numberInterpolator(start.alpha, end.alpha, time)
        currentStep++

        return SRGBColor(r, g, b, alpha)
    }
}