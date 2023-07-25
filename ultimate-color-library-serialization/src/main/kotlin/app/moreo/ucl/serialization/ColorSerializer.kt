package app.moreo.ucl.serialization

import app.moreo.ucl.colors.XYZD65Color
import app.moreo.ucl.enums.ColorType
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.descriptors.element
import kotlinx.serialization.encoding.*

class XYZD65ColorSerializer: KSerializer<XYZD65Color> {
    override val descriptor = buildClassSerialDescriptor("Color") {
        element<Float>("x")
        element<Float>("y")
        element<Float>("z")
        element<Float>("alpha")
    }

    override fun deserialize(decoder: Decoder): XYZD65Color {
        return decoder.decodeStructure(descriptor) {
            var x = XYZD65Color.D65_REFERENCE_WHITE.x
            var y = XYZD65Color.D65_REFERENCE_WHITE.y
            var z = XYZD65Color.D65_REFERENCE_WHITE.z
            var alpha = 1f
            while (true) {
                when (val index = decodeElementIndex(descriptor)) {
                    0 -> x = decodeFloatElement(descriptor, 0)
                    1 -> y = decodeFloatElement(descriptor, 1)
                    2 -> z = decodeFloatElement(descriptor, 2)
                    3 -> alpha = decodeFloatElement(descriptor, 3)
                    CompositeDecoder.DECODE_DONE -> break
                    else -> error("Unexpected index: $index")
                }
            }

            return@decodeStructure XYZD65Color(x, y, z, alpha)
        }
    }

    override fun serialize(encoder: Encoder, value: XYZD65Color) {
        val xyz = value.toSpace(ColorType.XYZ_D65)
        encoder.encodeStructure(descriptor) {
            encodeFloatElement(descriptor, 0, xyz.x)
            encodeFloatElement(descriptor, 1, xyz.y)
            encodeFloatElement(descriptor, 2, xyz.z)
            encodeFloatElement(descriptor, 3, xyz.alpha)
        }
    }
}