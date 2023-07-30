package app.moreo.ucl.utils

import kotlin.math.abs
import kotlin.math.floor


typealias NumberInterpolator = (Float, Float, Float) -> Float

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

internal fun Float.toRadians(): Float {
    return Math.toRadians(this.toDouble()).toFloat()
}

internal const val TWO_PI = 2 * Math.PI.toFloat()

internal fun Float.precisionEquals(other: Float, epsilon: Float = 0.01f): Boolean {
   return abs(this - other) < epsilon
}

internal fun Float.correct(): Float {
    return if (this < 0.001f) 0f else if (this > 1) 1f else this
}

internal fun Float.remap(from1: Float, to1: Float, from2: Float, to2: Float): Float {
    return (this - from1) / (to1 - from1) * (to2 - from2) + from2
}

internal val MINIMUM_FLOAT_DELTA = Math.ulp(1f)

internal const val LAB_DELTA = 6/29f