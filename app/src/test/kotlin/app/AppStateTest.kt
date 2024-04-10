package app

import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Assert
import org.junit.Test
import javax.inject.Inject

/**
 * Just checks that DI is initialized properly with [AppState] instance
 */
@HiltAndroidTest
class AppStateTest : BaseHiltUnitTest() {

    @Inject
    lateinit var appState: AppState

    @Test
    fun `DI instance exists`() {
        Assert.assertNotNull(appState)
    }

}