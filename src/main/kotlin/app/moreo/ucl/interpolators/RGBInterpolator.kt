package app.moreo.ucl.interpolators

import app.moreo.ucl.colors.RGBColor
import app.moreo.ucl.enums.InterpolationPath
import app.moreo.ucl.interfaces.ColorInterpolator
import app.moreo.ucl.utils.interpolate
import java.util.logging.Logger

class RGBInterpolator(override val start: RGBColor, override val end: RGBColor, override val steps: Int,
                      override val path: InterpolationPath, override val numberInterpolator: (Float, Float, Float) -> Float = ::interpolate):
    ColorInterpolator<RGBColor> {

    init {
        if (path != InterpolationPath.SHORTEST) Logger.getLogger("UltimateColorLibrary").warning("RGB interpolation only supports SHORTEST interpolation path. Ignoring interpolation path.")
    }

    private var currentStep = 0

    override fun hasNext(): Boolean {
        return currentStep < steps
    }

    override fun next(): RGBColor {
        val time = currentStep.toFloat() / (steps - 1)

        val r = numberInterpolator(start.red, end.red, time)
        val g = numberInterpolator(start.green, end.green, time)
        val b = numberInterpolator(start.blue, end.blue, time)
        val alpha = numberInterpolator(start.alpha, end.alpha, time)
        currentStep++

        return RGBColor(r, g, b, alpha)
    }
}