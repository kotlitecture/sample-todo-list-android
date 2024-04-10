# Usage

## Overview

The framework is pre-configured to properly work with DI inside of the `app` module.

Just extend your test class from the `app.BaseHiltUnitTest` and write test logic as usual.

## Example

```kotlin
@HiltAndroidTest
class AppStateTest : BaseHiltUnitTest() {

    @Inject
    lateinit var appState: AppState

    @Test
    fun `DI instance exists`() {
        Assert.assertNotNull(appState)
    }

}
```

### Usage outside of the app module

Add `:core:testing` as a dependency to the required module.

```groovy
testImplementation project(":core:testing")
```
