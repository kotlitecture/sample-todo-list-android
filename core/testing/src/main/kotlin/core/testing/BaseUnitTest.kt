package core.testing

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking

/**
 * Base class for Android unit tests.
 */
abstract class BaseUnitTest {

    /**
     * Performs a test using the provided block in a suspended coroutine scope.
     *
     * @param block The block of code to execute.
     */
    protected fun performTest(block: suspend CoroutineScope.() -> Unit) = runBlocking(Dispatchers.IO) {
        block()
    }

}