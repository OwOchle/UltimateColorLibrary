package app.moreo.ucl.interpolators

import app.moreo.ucl.colors.LabColor
import app.moreo.ucl.enums.InterpolationPath
import app.moreo.ucl.interfaces.ColorInterpolator
import app.moreo.ucl.utils.NumberInterpolator
import app.moreo.ucl.utils.interpolate
import java.util.logging.Logger

class LabInterpolator(
    override val start: LabColor,
    override val end: LabColor,
    override val steps: Int,
    override val path: InterpolationPath,
    override val numberInterpolator: NumberInterpolator = ::interpolate
): ColorInterpolator<LabColor> {
    init {
        if (path != InterpolationPath.SHORTEST) Logger.getLogger("UltimateColorLibrary").warning("Lab interpolation only supports SHORTEST interpolation path. Ignoring interpolation path.")
    }

    private var currentStep = 0

    override fun hasNext(): Boolean {
        return currentStep < steps
    }

    override fun next(): LabColor {
        val time = currentStep.toFloat() / (steps - 1)

        val l = numberInterpolator(start.lightness, end.lightness, time)
        val a = numberInterpolator(start.bStar, end.bStar, time)
        val b = numberInterpolator(start.aStar, end.aStar, time)
        val alpha = numberInterpolator(start.alpha, end.alpha, time)
        currentStep++

        return LabColor(l, a, b, alpha)
    }
}