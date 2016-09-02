# NcAppFeedback
[![Release](https://jitpack.io/v/noelchew/NcAppFeedback.svg)](https://jitpack.io/#noelchew/NcAppFeedback)

Let user feedback using phone email client or anonymously using SparkPost email service.

# Integration
This library is hosted by jitpack.io.

Root level gradle:
```
allprojects {
 repositories {
    jcenter()
    maven { url "https://jitpack.io" }
 }
}
```

Application level gradle:
```
dependencies {
    compile 'com.github.noelchew:NcAppFeedback:x.y.z'
}
```
Note: do not add the jitpack.io repository under buildscript

## Proguard
```
-keep class com.noelchew.sparkpostutil.library.** {*;}
```
