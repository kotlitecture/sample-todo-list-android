# Usage

## Overview

The API can be accessed through:
- `core.data.datasource.clipboard.ClipboardSource` - facade interface at the core module level.
- `app.datasource.clipboard.AppClipboardSource` - decorator class at the app level.

The difference is that the class serves as a **decorator** and can provide extra methods without impacting facade implementations.

Facade **ClipboardSource** provides the following methods:

- `getChanges(): Flow<String?>` - Retrieves changes made to the clipboard.
- `copy(text: String?, label: String?)` - Copies text to the clipboard.

## Example

Both the **facade** and **decorator** are pre-configured via dependency injection (DI) as singletons in `app.di.datasource.ProvidesClipboardSource`.

To start using, just inject it to your DI managed class.

```kotlin
@HiltViewModel
class TemplateViewModel @Inject constructor(
    private val clipboardSource: ClipboardSource // AppClipboardSource
) : BaseViewModel() {

    override fun doBind() {
        launchAsync("trackClipboard") {
            clipboardSource.getChanges().collectLatest {
                ...
            }
        }
    }
    
    ...

    fun onCopyText(text:String) {
        clipboardSource.copy(text)
    }

}
```
