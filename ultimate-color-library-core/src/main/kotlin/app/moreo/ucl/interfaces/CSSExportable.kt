package app.moreo.ucl.interfaces

import app.moreo.ucl.enums.CSSFormats

interface CSSExportable {
    fun toCSSString(includeAlpha: Boolean = false): String
}

interface CSSExportableMultipleFormats<T: CSSFormats> {
    fun toCSSString(format: T, includeAlpha: Boolean = false): String
}