package app.moreo.ucl.interfaces

import app.moreo.ucl.Color
import app.moreo.ucl.ColorInterpolation

interface Interpolatable<T: Color> {

    operator fun rangeTo(other: Color): ColorInterpolation<T>
}