package app.moreo.ucl.utils

internal val SHORT_HEX_RGB = Regex("#(?<red>[0-9a-f])(?<green>[0-9a-f])(?<blue>[0-9a-f])(?<alpha>[0-9a-f])?\$", RegexOption.IGNORE_CASE)
internal val LONG_HEX_RGB = Regex("#(?<red>[0-9a-f]{2})(?<green>[0-9a-f]{2})(?<blue>[0-9a-f]{2})(?<alpha>[0-9a-f]{2})?\$", RegexOption.IGNORE_CASE)
internal val CSS_RGB = Regex("rgba?\\( *(?<red>[0-9]{1,3}%?)[ ,]*(?<green>[0-9]{1,3}%?)[ ,]*(?<blue>[0-9]{1,3}%?) ?[,/]? ?(?<alpha>[0-9.]+%?)?\\)\$")
internal val CSS_HSL = Regex("hsla?\\( *(?<hue>[0-9]*(?:\\.[0-9])?)(?<hueUnit>deg|turn|rad|grad|)[ ,]*(?<saturation>[0-9]{1,3}%)[ ,]*(?<lightness>[0-9]{1,3}%)(?: ?[,/] ?(?<alpha>[0-9]*(?:\\.[0-9])?%?))?\\)\$")

internal enum class ParsedColorType {
    SHORT_HEX,
    LONG_HEX,
    CSS_RGB,
    CSS_HSL
}

internal val regexTypeList = listOf(
    SHORT_HEX_RGB to ParsedColorType.SHORT_HEX,
    LONG_HEX_RGB to ParsedColorType.LONG_HEX,
    CSS_RGB to ParsedColorType.CSS_RGB,
    CSS_HSL to ParsedColorType.CSS_HSL
)