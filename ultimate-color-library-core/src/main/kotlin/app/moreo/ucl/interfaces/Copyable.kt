package app.moreo.ucl.interfaces

import app.moreo.ucl.Color

interface Copyable<T: Color> {

    /**
     * Copies the color
     * @return The copy
     */
    fun copy(): T
}