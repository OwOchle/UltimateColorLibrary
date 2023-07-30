package app.moreo.ucl.utils

import kotlin.reflect.KProperty

internal class BoundedFloat(value: Float, private val minimum: Float, private val maximum: Float) {

    var value = if (maximum.precisionEquals(1f)) {
        value.coerceIn(minimum, maximum)
    } else {
        value.mod(maximum).coerceIn(minimum, maximum)
    }
        private set


    operator fun getValue(thisRef: Any?, property: KProperty<*>): Float {
        return value
    }

    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: Float) {
        if (maximum.precisionEquals(1f)) {
            this.value = value.coerceIn(minimum, maximum)
        } else {
            this.value = value.mod(maximum).coerceIn(minimum, maximum)
        }
    }
}