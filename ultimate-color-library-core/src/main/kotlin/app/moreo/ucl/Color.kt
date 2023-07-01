package app.moreo.ucl

import app.moreo.ucl.colors.HSLColor
import app.moreo.ucl.colors.SRGBColor
import app.moreo.ucl.enums.ColorType
import app.moreo.ucl.utils.*
import app.moreo.ucl.utils.ParsedColorType
import app.moreo.ucl.utils.TWO_PI
import app.moreo.ucl.utils.regexTypeList
import app.moreo.ucl.utils.remap
import app.moreo.ucl.utils.toRadians

interface Color {

    companion object {
        @JvmStatic
        var DEFAULT_COLOR_INTERPOLATOR_STEPS = 25

        /**
         * Parse a color from a string <b>WARNING: This method does not check if the format is valid as long as it matches the regex</b>
         * @param color The color string
         * @param space The wanted output space
         * @return color
         */
        @JvmStatic
        fun <T: Color> fromString(color: String, space: ColorType<T>): T {
            val cleanInput = color.trim()

            var stringType: ParsedColorType? = null
            var groups: MatchNamedGroupCollection? = null

            for ((regex, type) in regexTypeList) {
                val res = regex.matchEntire(cleanInput)
                if (res != null) {
                    stringType = type
                    groups = res.groups as MatchNamedGroupCollection
                    break
                }
            }

            if (stringType == null) throw IllegalArgumentException("Invalid color format")
            if (groups == null) throw IllegalArgumentException("Invalid color format")

            @Suppress("REDUNDANT_ELSE_IN_WHEN")
            return when (stringType) {
                ParsedColorType.SHORT_HEX -> {
                    val red = groups["red"]?.value?.repeat(2) ?: throw IllegalArgumentException("Invalid color format")
                    val green = groups["green"]?.value?.repeat(2) ?: throw IllegalArgumentException("Invalid color format")
                    val blue = groups["blue"]?.value?.repeat(2) ?: throw IllegalArgumentException("Invalid color format")
                    val alpha = groups["alpha"]?.value?.repeat(2) ?: "FF"

                    SRGBColor(
                        red.toShort(16),
                        green.toShort(16),
                        blue.toShort(16),
                        alpha.toShort(16).div(255f)
                    ).toSpace(space)
                }

                ParsedColorType.LONG_HEX -> {
                    val red = groups["red"]?.value ?: throw IllegalArgumentException("Invalid color format")
                    val green = groups["green"]?.value ?: throw IllegalArgumentException("Invalid color format")
                    val blue = groups["blue"]?.value ?: throw IllegalArgumentException("Invalid color format")
                    val alpha = groups["alpha"]?.value ?: "FF"

                    SRGBColor(
                        red.toShort(16),
                        green.toShort(16),
                        blue.toShort(16),
                        alpha.toShort(16).div(255f)
                    ).toSpace(space)
                }

                ParsedColorType.CSS_RGB -> {
                    val redString = groups["red"]?.value ?: throw IllegalArgumentException("Invalid color format")
                    val greenString = groups["green"]?.value ?: throw IllegalArgumentException("Invalid color format")
                    val blueString = groups["blue"]?.value ?: throw IllegalArgumentException("Invalid color format")
                    val alphaString = groups["alpha"]?.value ?: "1"

                    val red = if (redString.endsWith("%")) {
                        redString.dropLast(1).toFloat().div(100f).times(255f).toInt().toShort()
                    } else {
                        redString.toShort()
                    }

                    val green = if (greenString.endsWith("%")) {
                        greenString.dropLast(1).toFloat().div(100f).times(255f).toInt().toShort()
                    } else {
                        greenString.toShort()
                    }

                    val blue = if (blueString.endsWith("%")) {
                        blueString.dropLast(1).toFloat().div(100f).times(255f).toInt().toShort()
                    } else {
                        blueString.toShort()
                    }

                    val alpha = if (alphaString.endsWith("%")) {
                        alphaString.dropLast(1).toFloat().div(100f)
                    } else {
                        alphaString.toFloat()
                    }

                    return SRGBColor(
                        red,
                        green,
                        blue,
                        alpha
                    ).toSpace(space)
                }

                ParsedColorType.CSS_HSL -> {
                    val hueString = groups["hue"]?.value ?: throw IllegalArgumentException("Invalid color format")
                    val saturationString = groups["saturation"]?.value ?: throw IllegalArgumentException("Invalid color format")
                    val lightnessString = groups["lightness"]?.value ?: throw IllegalArgumentException("Invalid color format")
                    val alphaString = groups["alpha"]?.value ?: "1"

                    val hueUnit = groups["hueUnit"]?.value ?: throw IllegalArgumentException("Invalid color format")

                    val hue = if (hueUnit == "deg" || hueUnit.isEmpty()) {
                        hueString.toFloat().mod(360f).toRadians()
                    } else if (hueUnit == "rad") {
                        hueString.toFloat()
                    } else if (hueUnit == "turn") {
                        hueString.toFloat().mod(1f).remap(0f, 1f, 0f, TWO_PI)
                    } else if (hueUnit == "grad") {
                        hueString.toFloat().mod(400f).remap(0f, 400f, 0f, TWO_PI)
                    } else {
                        throw IllegalArgumentException("Invalid hue unit")
                    }

                    val saturation = if (saturationString.endsWith("%")) {
                        saturationString.dropLast(1).toFloat().div(100f)
                    } else {
                        saturationString.toFloat()
                    }

                    val lightness = if (lightnessString.endsWith("%")) {
                        lightnessString.dropLast(1).toFloat().div(100f)
                    } else {
                        lightnessString.toFloat()
                    }

                    val alpha = if (alphaString.endsWith("%")) {
                        alphaString.dropLast(1).toFloat().div(100f)
                    } else {
                        alphaString.toFloat()
                    }

                    return HSLColor(
                        hue,
                        saturation,
                        lightness,
                        alpha
                    ).toSpace(space)
                }

                else -> TODO("Not yet implemented")
            }
        }
    }

    var alpha: Float

    /**
     * Convert this color to another color space
     * @param color The color space to convert to
     * @return The converted color
     */
    fun <T: Color> toSpace(color: ColorType<T>): T

    override operator fun equals(other: Any?): Boolean
}