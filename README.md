# The Ultimate Color Library
[![Test status](https://img.shields.io/github/actions/workflow/status/MoreOwO/UltimateColorLibrary/gradle-test.yml?style=for-the-badge&logo=github&label=Tests)](https://github.com/MoreOwO/UltimateColorLibrary/actions/workflows/gradle-test.yml)
[![Sonatype status](https://img.shields.io/nexus/snapshots/app.moreo/ultimate-color-library-core?server=https%3A%2F%2Fs01.oss.sonatype.org&style=for-the-badge&label=NEXUS-SNAPSHOT)](https://s01.oss.sonatype.org/content/repositories/snapshots/app/moreo/ultimate-color-library-core/)
[![Sonatype status](https://img.shields.io/nexus/releases/app.moreo/ultimate-color-library-core?server=https%3A%2F%2Fs01.oss.sonatype.org&style=for-the-badge&label=NEXUS-RELEASE)](https://s01.oss.sonatype.org/content/repositories/releases/app/moreo/ultimate-color-library-core/)
[![Maven central status](https://img.shields.io/maven-metadata/v.svg?label=maven-central&metadataUrl=https%3A%2F%2Frepo1.maven.org%2Fmaven2%2Fapp%2Fmoreo%2Fultimate-color-library-core%2Fmaven-metadata.xml&style=for-the-badge)](https://central.sonatype.com/artifact/app.moreo/ultimate-color-library-core/)
[![License](https://img.shields.io/badge/LICENSE-CC--BY--SA-%23EF9421?style=for-the-badge&logo=creativecommons)](http://creativecommons.org/licenses/by-sa/4.0/)
[![Modrinth](https://img.shields.io/modrinth/dt/ultimate-color-library?style=for-the-badge&logo=modrinth)](https://modrinth.com/mod/ultimate-color-library)

## Concept
The ultimate color library was created because I found myself unable to easily convert colors and make gradients.
The origin of my idea is the js library [Color.js](https://colorjs.io/)

As of now, the library is Ultimate only by name, but I hope to be able to support the most color spaces possible and turn it into a real Ultimate Color Library.

## Installation

The latest version is ``0.0.10-alpha-SNAPSHOT``

The latest stable version is ``0.0.6-alpha``

Branches available :
- ``core``
  - ``minecraft``
    - ``bukkit``
    - ``fabric``
  - ``serialization``

The indentation represent the dependencies of branches on each other, you just need to import the highest branch you need to have access to all the upper branches. i.e. if you install ``bukkit``, you will get access to ``minecraft`` and ``core`` dependencies.

<details>
<summary> Gradle Groovy </summary>

```groovy
repositories {
    maven {
        url = "https://s01.oss.sonatype.org/content/repositories/snapshots/"
    } // For snapshots
    mavenCentral() // For releases
}

dependencies {
    implementation("app.moreo:ultimate-color-library-BRANCH:CURRENT_VERSION")
}
```
</details>

<details>
<summary> Gradle Kotlin</summary>

```kotlin
repositories {
    maven("https://s01.oss.sonatype.org/content/repositories/snapshots/") // For snapshots
    mavenCentral() // For releases
}

dependencies {
    implementation("app.moreo:ultimate-color-library-BRANCH:CURRENT_VERSION")
}
```
</details>

## Usage

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
If you need to transform a color into another, just use the `toSpace` method and pass the color space you want

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
- XYZ
- LAB

## Planned Color Spaces
- CMYK
- LCH

## Planned Features
- [ ] Kotlin Multiplatform
- [ ] Color conversion to other color types (i.e. Bukkit, JavaFX, etc.)
- [ ] String parsing (i.e. #FF0000, rgb(255, 0, 0), etc.)
- [ ] String export (i.e. #FF0000, rgb(255, 0, 0), etc.)


## Thanks
- Bruce Justin Lindbloom for their [color conversion equations](http://www.brucelindbloom.com/index.html?Math.html)
- ColorMine for their [color converter](https://colormine.org/color-converter) which is the one I use to create the tests

## Links
- [Javadoc](https://docs.moreo.app/ucl/javadoc)
- [Maven Central](https://central.sonatype.com/artifact/app.moreo/ultimate-color-library-core/)

## License
<a rel="license" href="http://creativecommons.org/licenses/by-sa/4.0/"><img alt="Creative Commons License" style="border-width:0" src="https://i.creativecommons.org/l/by-sa/4.0/88x31.png" /></a><br />This work is licensed under a <a rel="license" href="http://creativecommons.org/licenses/by-sa/4.0/">Creative Commons Attribution-ShareAlike 4.0 International License</a>.
