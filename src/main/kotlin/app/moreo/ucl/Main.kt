package app.moreo.ucl

import app.moreo.ucl.colors.HSLColor
import app.moreo.ucl.enums.ColorType
import app.moreo.ucl.enums.InterpolationPath

fun main() {
    val color = HSLColor(60, 100, 50)
    val color2 = HSLColor(0, 100, 50)
    var count = 0
    println("color: $color")
    for (x in color..color2 numberInterpolator { a, b, t -> a + (b - a) * t } steps 100 space ColorType.HSV path InterpolationPath.LONGEST) {
        println(x)
        count++
    }

    println("color2: $color2")
    println("count: $count")
}