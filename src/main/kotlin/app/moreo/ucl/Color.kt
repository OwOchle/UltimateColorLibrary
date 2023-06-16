package app.moreo.ucl

import app.moreo.ucl.enums.ColorType

interface Color {

    companion object {
        @JvmStatic
        var DEFAULT_COLOR_INTERPOLATOR_STEPS = 25
    }

    var alpha: Float

    @Deprecated("Use toSpace instead", ReplaceWith("toSpace(color)"))
    fun <T: Color> toColor(color: ColorType<T>): T

    fun <T: Color> toSpace(color: ColorType<T>): T
}