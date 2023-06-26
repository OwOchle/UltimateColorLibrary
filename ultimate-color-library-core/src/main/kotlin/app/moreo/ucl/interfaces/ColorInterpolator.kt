package app.moreo.ucl.interfaces

import app.moreo.ucl.enums.InterpolationPath

/**
 * Represents a color interpolator
 * @param T the color type
 * @property start the start color
 * @property end the end color
 * @property steps the number of steps to interpolate
 * @property path the [InterpolationPath] to use
 * @property numberInterpolator the number interpolator to use
 */
interface ColorInterpolator<T: Color>: Iterator<T> {
    val start: T
    val end: T
    val steps: Int
    val path: InterpolationPath
    val numberInterpolator: (Float, Float, Float) -> Float
}