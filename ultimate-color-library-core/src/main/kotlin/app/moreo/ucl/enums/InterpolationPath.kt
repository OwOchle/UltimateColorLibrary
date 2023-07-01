package app.moreo.ucl.enums

/**
 * An enumeration of the interpolation paths
 *
 * Note: SRGB does not support LONGEST and shortest will be used in any SRGB interpolations
 * @property SHORTEST the shortest path
 * @property LONGEST the longest path
 */
enum class InterpolationPath {
    SHORTEST,
    LONGEST
}