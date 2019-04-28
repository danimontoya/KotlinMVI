package com.danieh.kotlinmvi

import android.app.Application
import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.danieh.kotlinmvi.features.presentation.MainActivity
import org.junit.Rule
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config

/**
 * Base class for Android tests. Inherit from it to create test cases which contain android
 * framework dependencies or components.
 *
 * @see UnitTest
 */
@RunWith(RobolectricTestRunner::class)
@Config(
    constants = BuildConfig::class,
    application = AndroidTest.ApplicationStub::class,
    sdk = [23],
    manifest = Config.NONE
)
abstract class AndroidTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Suppress("LeakingThis")
    @JvmField
    val injectMocks = InjectMocksRule.create(this@AndroidTest)

    fun context(): Context = RuntimeEnvironment.application

    fun activityContext(): Context = mock(MainActivity::class.java)

    internal class ApplicationStub : Application()
}
