# The Ultimate Color Library

## Concept
The ultimate color library was created because I found myself unable to easily convert colors and make gradients.
The origin of my idea is the js library [Color.js](https://colorjs.io/)

As of now, the library is Ultimate only by name, but I hope to be able to support the most color spaces possible and turn it into a real Ultimate Color Library.

## Usage

<div style="display: none">
<details>
<summary>Kotlin</summary>

```kotlin
```
</details>

<details>
<summary>Java</summary>

```java
class Main {
    public static void main(String[] args) {
    }
}
```
</details>
</div>

### Creating a color
Just create the color with the space you need. i.e. :
<details>
<summary>Kotlin</summary>

```kotlin
import app.moreo.ucl.colors.RGBColor

val color = RGBColor(1.0, 0.0, 0.0, 0.0)
```
</details>

<details>
<summary>Java</summary>

```java
import app.moreo.ucl.colors.RGBColor;

class Main {
    public static void main(String[] args) {
        RGBColor color = new RGBColor(1.0, 0.0, 0.0, 0.0);
    }
}
```
</details>

### Color conversion
If you need to transform a color into another, just use the `toColor` method and pass the color space you want

<details>
<summary>Kotlin</summary>

```kotlin
import app.moreo.ucl.enums.ColorType
import app.moreo.ucl.colors.*

val color: RGBColor = RGBColor(1.0, 1.0, 0.0, 1.0) // RGBColor(red=1.0, green=1.0, blue=0.0, alpha=1.0)
val hsvColor: HSVColor = color.toColor(ColorType.HSV) // HSVColor(hue=1.0471976, saturation=1.0, value=1.0, alpha=1.0)
```
</details>

<details>
<summary>Java</summary>

```java
import app.moreo.ucl.enums.ColorType;
import app.moreo.ucl.colors.*;

class Main {
    public static void main(String[] args) {
        RGBColor color = new RGBColor(1.0, 1.0, 0.0, 1.0); // RGBColor(red=1.0, green=1.0, blue=0.0, alpha=1.0)
        HSVColor hsvColor = color.toColor(ColorType.HSV); // HSVColor(hue=1.0471976, saturation=1.0, value=1.0, alpha=1.0)
    }
}
```
</details>

### Color interpolation
If you need to interpolate between two colors, you can use the `rangeTo` kotlin operator or the `rangeTo` method in java.
The rangeTo returns a `ColorInterpolation` which is iterable and can be configured as you need. 
It's default interpolation space is the type of the first color of the range.

Note that every configuration function of ``ColorInterpolation`` returns itself, so you can chain calls. Every function is also infix to improve readability in Kotlin.

The default step count is 25 and is set by the `Color.DEFAULT_COLOR_INTERPOLATOR_STEPS` variable, you can change it to default every range with this number of steps. But you can also change it by using the `steps` method.

You can change the interpolation method with the `numberInterpolator` method. It takes two float (a, b) and a t value which is a representation of the advancement of the interpolation.

You can change the interpolation space with the `space` method. It takes a `ColorType` which is the space you want to interpolate in. The returned color type will be the one defined by the `ColorType`.
<details>
<summary>Kotlin</summary>

```kotlin
import app.moreo.ucl.enums.ColorType
import app.moreo.ucl.colors.*

val color1: RGBColor = RGBColor(1.0, 0.0, 0.0, 1.0)
val color2: HSLColor = HSLColor(120, 100, 100, 1.0)

for (x in color1..color2 steps 10 numberInterpolator { a, b, t -> a + (b - a) * t } space ColorType.HSL path InterpolationPath.LONGEST) {
    println(x)
}
```
</details>

<details>
<summary>Java</summary>

```java
import app.moreo.ucl.enums.ColorType;
import app.moreo.ucl.colors.*;
import org.w3c.dom.css.RGBColor;

class Main {
    public static void main(String[] args) {
        RGBColor color1 = RGBColor(1.0, 0.0, 0.0, 1.0);
        HSLColor color2 = HSLColor(120, 100, 100, 1.0);
        
        for (HSLColor x: color1.rangeTo(color2).steps(10).numberInterpolator((Float a, Float b, Float t) -> a + (b - a) * t ).space(ColorType.HSL).path(InterpolationPath.LONGEST)) {
            System.out.println(x);
        }
    }
}
```
</details>

## Supported Color Spaces
- RGB
- HSV (HSB)
- HSL

## Planned Color Spaces
- CMYK
- XYZ
- LAB
- LCH

## License
<a rel="license" href="http://creativecommons.org/licenses/by-sa/4.0/"><img alt="Creative Commons License" style="border-width:0" src="https://i.creativecommons.org/l/by-sa/4.0/88x31.png" /></a><br />This work is licensed under a <a rel="license" href="http://creativecommons.org/licenses/by-sa/4.0/">Creative Commons Attribution-ShareAlike 4.0 International License</a>.
