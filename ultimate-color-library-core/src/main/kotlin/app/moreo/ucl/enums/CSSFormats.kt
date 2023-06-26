package app.moreo.ucl.enums

interface CSSFormats

/**
 * Enum class representing the different formats for RGB css representation
 * @property HEX Hexadecimal representation (automatically chooses between short and long)
 * @property LONG_HEX Long hexadecimal representation (forces long representation)
 * @property PERCENT Percentage representation <code>rgb(100% 100% 100%)</code>
 * @property DECIMAL Decimal representation <code>rgb(255 255 255)</code>
 */
enum class RGBFormats: CSSFormats {
    HEX,
    LONG_HEX,
    PERCENT,
    DECIMAL
}