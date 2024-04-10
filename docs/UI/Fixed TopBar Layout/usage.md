# Usage

## Overview

Component: `app/ui/container/FixedTopBarLayout.kt`

## Example

```kotlin
@Composable
fun TemplateScreen() {
    val viewModel: TemplateViewModel = provideHiltViewModel()
    FixedTopBarColumnLayout(
        title = "MyScreen",
        onBack = viewModel::onBack,
        actions = {
                  
        },
        content = {

        }
    )
}
```
