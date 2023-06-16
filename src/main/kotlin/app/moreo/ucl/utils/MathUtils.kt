package app.moreo.ucl.utils

import kotlin.math.floor

/**
 * Linearly interpolates between two values.
 * @param a The first value.
 * @param b The second value.
 * @param t The interpolation value. Should be between 0 and 1.
 * @return The interpolated value.
 */
internal fun interpolate(a: Float, b: Float, t: Float): Float {
    return a + (b - a) * t
}

internal fun Float.toIntFloor(): Int {
    return floor(this).toInt()
}

internal const val TWO_PI = 2 * Math.PI.toFloat()