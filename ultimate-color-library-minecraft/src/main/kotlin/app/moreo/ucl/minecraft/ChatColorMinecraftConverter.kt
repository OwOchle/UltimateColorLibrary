package app.moreo.ucl.minecraft

import app.moreo.ucl.Color
import app.moreo.ucl.enums.ColorType

val SINGLE_CHARACTER_REGEX = Regex("(.)")

/**
 * Converts a color to a Minecraft color usable in chat
 * @param format The format to use
 * @return The color as a string
 */
@JvmOverloads
fun Color.toChatColor(format: ChatColorFormat = ChatColorFormat.AMPERSAND): String {
    return colorToChatColor(this, format)
}

/**
 * Converts a color to a Minecraft color usable in chat
 * @param format The format to use
 * @return The color as a string
 */
@JvmOverloads
fun colorToChatColor(color: Color, format: ChatColorFormat = ChatColorFormat.AMPERSAND): String {
    val rgbColor = color.toSpace(ColorType.SRGB)
    return when(format) {
        ChatColorFormat.AMPERSAND -> {
            "&#" + (rgbColor.red * 255).toInt().toString(16).padStart(2, '0') + (rgbColor.green * 255).toInt().toString(16).padStart(2, '0') + (rgbColor.blue * 255).toInt().toString(16).padStart(2, '0')
        }

        ChatColorFormat.AMPERSAND_LEGACY -> {
            "&x" + (rgbColor.red * 255).toInt().toString(16).padStart(2, '0').replace(SINGLE_CHARACTER_REGEX, "&$1") +
                    (rgbColor.green * 255).toInt().toString(16).padStart(2, '0').replace(SINGLE_CHARACTER_REGEX, "&$1") +
                    (rgbColor.blue * 255).toInt().toString(16).padStart(2, '0').replace(SINGLE_CHARACTER_REGEX, "&$1")
        }

        ChatColorFormat.SECTION -> {
            "§x" + (rgbColor.red * 255).toInt().toString(16).padStart(2, '0').replace(SINGLE_CHARACTER_REGEX, "§$1") +
                    (rgbColor.green * 255).toInt().toString(16).padStart(2, '0').replace(SINGLE_CHARACTER_REGEX, "§$1") +
                    (rgbColor.blue * 255).toInt().toString(16).padStart(2, '0').replace(SINGLE_CHARACTER_REGEX, "§$1")
        }
    }.lowercase()
}