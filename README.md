# SwitchBox Android plugin

If you add a lot of fields to BuildConfig class via gradle this plugin is for you.

Just add to your gradle script this block of code:

```
switchBox {
    filePath = "path-to-your-file"
}
```

Now create a file with JSON containing your fields like this:

```
{
  "prop1": {
    "default": true,
    "alpha": false
  },
  "prop2": {
    "default": "def",
    "alpha": "alpha",
    "debug": "non-def"
  },
  "prop3": {
    "default": 1,
    "debug": 2
  }
}
```

### Apply plugin
Build script snippet for use in all Gradle versions:
```
buildscript {
  repositories {
    maven {
      url "https://plugins.gradle.org/m2/"
    }
  }
  dependencies {
    classpath "gradle.plugin.com.github.nomensvyat:SwitchBoxPlugin:0.1.0"
  }
}

apply plugin: "com.github.nomensvyat.switchbox"
```
Build script snippet for new, incubating, plugin mechanism introduced in Gradle 2.1:
```
plugins {
  id "com.github.nomensvyat.switchbox" version "0.1.0"
}
```
