package app.moreo.ucl.enums

import app.moreo.ucl.Color
import app.moreo.ucl.colors.HSLColor
import app.moreo.ucl.colors.HSVColor
import app.moreo.ucl.colors.RGBColor
import app.moreo.ucl.colors.XYZD65Color

class ColorType<T: Color> {
    companion object {
        @JvmField
        val RGB = ColorType<RGBColor>()

        @JvmField
        val HSV = ColorType<HSVColor>()

        @JvmField
        val HSL = ColorType<HSLColor>()

        @JvmField
        val HSB = ColorType<HSVColor /* HSV = HSB */>()

        @JvmField
        val XYZ_D65 = ColorType<XYZD65Color>()
    }
}