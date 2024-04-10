package app

import android.os.Build
import androidx.test.ext.junit.runners.AndroidJUnit4
import core.testing.BaseAndroidUnitTest
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.HiltTestApplication
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

/**
 * Base class for Hilt unit tests.
 */
@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
@Config(
    application = HiltTestApplication::class,
    manifest = Config.NONE,
    sdk = [Build.VERSION_CODES.N]
)
abstract class BaseHiltUnitTest : BaseAndroidUnitTest() {

    /**
     * Rule for setting up Hilt for the test.
     */
    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    /**
     * Executes before each test to inject dependencies using Hilt.
     */
    @Before
    fun beforeTest() {
        hiltRule.inject()
    }

}