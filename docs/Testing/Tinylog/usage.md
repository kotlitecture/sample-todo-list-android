# Usage

## Overview

**tinylog** has a static logger class `org.tinylog.Logger`. Therefore, it is not necessary to create a logger instance for each class in which you want to issue log entries.

The app includes pre-configured file `app/src/main/resources/tinylog.properties` which can be changed following [the official documentation](https://tinylog.org/v2/configuration/).

```properties
writer        = console
writer.level  = debug
writer.format = [MY][{thread}] {message}
writingthread = true
```

## Example

```kotlin
import org.tinylog.Logger

class Application {

    @JvmStatic
    fun main(args: Array<String>) {
        Logger.debug("Hello World!")
    }

}
```
