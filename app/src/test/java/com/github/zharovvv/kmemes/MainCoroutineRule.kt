package com.github.zharovvv.kmemes

import com.github.zharovvv.kmemes.core.coroutines.CoroutineDispatchers
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.rules.TestWatcher
import org.junit.runner.Description

@ExperimentalCoroutinesApi
class MainCoroutineRule(
    private val testDispatcher: TestDispatcher = UnconfinedTestDispatcher()
) : TestWatcher() {

    val testCoroutineDispatchers: CoroutineDispatchers = TestCoroutineDispatchers(testDispatcher)

    override fun starting(description: Description) {
        super.starting(description)
        Dispatchers.setMain(testDispatcher)
    }

    override fun finished(description: Description) {
        super.finished(description)
        Dispatchers.resetMain()
    }

    private class TestCoroutineDispatchers(
        private val dispatcher: TestDispatcher
    ) : CoroutineDispatchers {

        override val ioDispatcher: CoroutineDispatcher get() = dispatcher
        override val mainDispatcher: CoroutineDispatcher get() = dispatcher
        override val computationDispatcher: CoroutineDispatcher get() = dispatcher
    }
}