package app.moreo.ucl.enums

import app.moreo.ucl.Color
import app.moreo.ucl.colors.*

class ColorType<T: Color> {
    companion object {
        @JvmField
        val SRGB = ColorType<SRGBColor>()

        @JvmField
        val HSV = ColorType<HSVColor>()

        @JvmField
        val HSL = ColorType<HSLColor>()

        @JvmField
        val HSB = ColorType<HSVColor /* HSV = HSB */>()

        @JvmField
        val XYZ_D65 = ColorType<XYZD65Color>()

        @JvmField
        val LAB = ColorType<LabColor>()
    }

    override fun toString(): String {
        return when (this) {
            SRGB -> "sRGB"
            HSV -> "HSV"
            HSL -> "HSL"
            HSB -> "HSB"
            XYZ_D65 -> "XYZ_D65"
            LAB -> "L*a*b*"
            else -> "Unknown"
        }
    }
}