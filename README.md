# NcAppFeedback
[![Release](https://jitpack.io/v/noelchew/NcAppFeedback.svg)](https://jitpack.io/#noelchew/NcAppFeedback)

Let user feedback using phone email client or anonymously using SparkPost email service.

[View example](https://github.com/NoelChew/NcAppFeedback/blob/master/app/src/main/java/com/noelchew/ncappfeedback/MainActivity.java)

[More info on setting up SparkPost account](https://github.com/NoelChew/android-sparkpost/blob/master/README.md#account-setup)

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

[![Release](https://jitpack.io/v/noelchew/NcAppFeedback.svg)](https://jitpack.io/#noelchew/NcAppFeedback)

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
