package app.moreo.ucl

import app.moreo.ucl.enums.ColorType

interface Color {

    companion object {
        @JvmStatic
        var DEFAULT_COLOR_INTERPOLATOR_STEPS = 25
    }

    var alpha: Float

    fun <T: Color> toColor(color: ColorType<T>): T

}