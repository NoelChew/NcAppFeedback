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
    implementation 'com.github.noelchew:NcAppFeedback:x.y.z'
    implementation 'com.github.noelchew:android-sparkpost:0.2.1'	
    implementation 'com.squareup.okhttp3:okhttp:4.2.2'	
    implementation 'com.google.code.gson:gson:2.8.6'
}
```
Note: do not add the jitpack.io repository under buildscript

## Proguard
```
-keep class com.noelchew.sparkpostutil.library.** {*;}
```
