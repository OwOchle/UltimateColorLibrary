package app.moreo.ucl.interfaces

import app.moreo.ucl.Color
import app.moreo.ucl.enums.InterpolationPath

interface ColorInterpolator<T: Color>: Iterator<T> {
    val start: T
    val end: T
    val steps: Int
    val path: InterpolationPath
    val numberInterpolator: (Float, Float, Float) -> Float
}