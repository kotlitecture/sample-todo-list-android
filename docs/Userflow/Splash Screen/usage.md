# Usage

## Overview

The API comes pre-configured with default theme settings. To customize the background color of the splash screen and icon, simply edit the `Theme.App.Splash` theme in the `app/src/main/res/values/themes.xml` file.

## Example

Here is the default theme configured to be used as the splash screen:

```xml
<?xml version="1.0" encoding="utf-8"?>
<resources xmlns:tools="http://schemas.android.com/tools">
    <style name="Theme.App.Splash" parent="Theme.SplashScreen">
        <item name="windowSplashScreenBackground">#FF394B3C</item>
        <item name="windowSplashScreenAnimatedIcon">@drawable/ic_launcher_foreground</item>
        <item name="postSplashScreenTheme">@style/Theme.App</item>
    </style>
</resources>
```

You can customize it further according to your requirements.
