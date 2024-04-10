# Usage

## Overview

Component package: `app.userflow.theme.change`

## Example

### As a separate screen

Invoke the `ChangeThemeDestination` destination from your **ViewModel** or **View** utilizing the pre-configured **NavigationState**.

```kotlin
@HiltViewModel
class TemplateViewModel @Inject constructor(
    private val navigationState: NavigationState
) : BaseViewModel() {

    fun onChangeTheme() {
        navigationState.onNext(ChangeThemeDestination)
    }

}
```

### As a dialog

Invoke the `ChangeThemeDialogDestination` destination from your **ViewModel** or **View** utilizing the pre-configured **NavigationState**.

```kotlin
@HiltViewModel
class TemplateViewModel @Inject constructor(
    private val navigationState: NavigationState
) : BaseViewModel() {

    fun onChangeTheme() {
        navigationState.onNext(ChangeThemeDialogDestination)
    }

}
```
