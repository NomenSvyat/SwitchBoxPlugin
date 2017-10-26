# SwitchBox Android plugin

If you add a lot of fields to BuildConfig class via gradle this plugin is for you.

Just add to your gradle build script this block of code:

```
switchBox {
    filePath = "path-to-your-file"
    [syncOnBuild = false (default is true)]
}
```

Now create a file with JSON containing your fields like this:

```
{
  "PROP_1": {
    "default": true,
    "alpha": false
  },
  "PROP_2": {
    "default": "def",
    "alpha": "alpha",
    "debug": "non-def"
  },
  "PROP_3": {
    "default": 1,
    "debug": 2
  }
}
```

### Versioning
XX.YY.ZZ
First 2 numbers (XX and YY) in version indicate support of Android Gradle plugin version.

### Apply plugin
You can look for latest plugin version [here](https://plugins.gradle.org/plugin/com.github.nomensvyat.switchbox)

Build script snippet for use in all Gradle versions:
```
buildscript {
  repositories {
    maven {
      url "https://plugins.gradle.org/m2/"
    }
  }
  dependencies {
    classpath "gradle.plugin.com.github.nomensvyat:SwitchBoxPlugin:$latestVersion"
  }
}

apply plugin: "com.github.nomensvyat.switchbox"
```
Build script snippet for new, incubating, plugin mechanism introduced in Gradle 2.1:
```
plugins {
  id "com.github.nomensvyat.switchbox" version latestVersion
}
```
