import app.moreo.ucl.colors.SRGBColor
import app.moreo.ucl.colors.XYZD65Color
import app.moreo.ucl.enums.ColorType
import app.moreo.ucl.serialization.XYZD65ColorSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.Json.Default.encodeToString
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class SerializationTest {
    private val color = SRGBColor(127, 127, 127)
    private val xyz = XYZD65Color(0.2512f, 0.348f, 0.1481f)

    @Serializable
    data class TestDataClass(val color: @Serializable(with = XYZD65ColorSerializer::class) XYZD65Color, val xyz: @Serializable(with = XYZD65ColorSerializer::class) XYZD65Color)

    @Test
    fun `export to json`() {
        assertEquals("""{"x":0.20172532,"y":0.21223073,"z":0.23111926,"alpha":1.0}""", encodeToString(XYZD65ColorSerializer(), color.toSpace(
            ColorType.XYZ_D65)), "JSON export from rgb is not correct")

        assertEquals("""{"x":0.2512,"y":0.348,"z":0.1481,"alpha":1.0}""", encodeToString(XYZD65ColorSerializer(), xyz), "JSON export from xyz is not correct")
    }

    @Test
    fun `load from json`() {
        assertEquals(color, Json.decodeFromString(XYZD65ColorSerializer(), """{"x":0.20172532,"y":0.21223073,"z":0.23111926,"alpha":1.0}""").toSpace(
            ColorType.SRGB), "JSON import to rgb is not correct")

        assertEquals(xyz, Json.decodeFromString(XYZD65ColorSerializer(), """{"x":0.2512,"y":0.348,"z":0.1481,"alpha":1.0}"""), "JSON import to xyz is not correct")
    }

    @Test
    fun `using serializable with`() {
        assertEquals("""{"color":{"x":0.20172532,"y":0.21223073,"z":0.23111926,"alpha":1.0},"xyz":{"x":0.2512,"y":0.348,"z":0.1481,"alpha":1.0}}""", Json.encodeToString<TestDataClass>(TestDataClass(color.toSpace(
            ColorType.XYZ_D65), xyz)), "JSON export from rgb is not correct")

        assertEquals(TestDataClass(color.toSpace(ColorType.XYZ_D65), xyz),  Json.decodeFromString<TestDataClass>("""{"color":{"x":0.20172532,"y":0.21223073,"z":0.23111926,"alpha":1.0},"xyz":{"x":0.2512,"y":0.348,"z":0.1481,"alpha":1.0}}"""))
    }
}