package com.github.zharovvv.kmemes.core.architecture.elm.ui

import com.github.zharovvv.kmemes.MainCoroutineRule
import com.github.zharovvv.kmemes.core.architecture.elm.model.Result
import com.github.zharovvv.kmemes.core.architecture.elm.store.Actor
import com.github.zharovvv.kmemes.core.architecture.elm.store.StateReducer
import com.github.zharovvv.kmemes.core.coroutines.CoroutineDispatchers
import com.google.common.truth.Truth.assertThat
import io.mockk.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ElmViewModelTest {

    //region Rule
    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()
    //endregion

    //region Mocks
    private val initialState: TestState = mockk()
    private val reducer: StateReducer<TestEvent, TestState, TestEffect, TestCommand> = mockk()
    private val actor: Actor<TestCommand, TestEvent> = mockk()
    //endregion

    private lateinit var elmViewModel: ElmViewModel<TestEvent, TestState, TestEffect, TestCommand>

    @Before
    fun setUp() {
        val coroutineDispatchers: CoroutineDispatchers = mockk {
            every { computationDispatcher } returns Dispatchers.Default
        }
        elmViewModel = ElmViewModel(
            initialState = initialState,
            reducer = reducer,
            actor = actor,
            coroutineDispatchers = coroutineDispatchers
        )
    }

    //region Tests
    @Test
    fun `verify init block`() {
        val actual = elmViewModel.states.value
        assertThat(actual).isEqualTo(initialState)
    }

    @Test
    fun `verify accept event then reducer return result only with state`() = runTest {
        val newState: TestState = mockk()
        every { reducer.reduce(any(), any()) } returns Result(newState)
        val allEffects = mutableListOf<TestEffect>()
        val collectJob = launch {
            elmViewModel.effects.toList(allEffects)
        }

        assertThat(elmViewModel.states.value).isEqualTo(initialState)

        val testUiEvent: TestEvent = mockk()
        elmViewModel.accept(testUiEvent)
        assertThat(elmViewModel.states.value).isEqualTo(newState)

        verifyAll {
            reducer.reduce(eq(testUiEvent), eq(initialState))
        }
        assertThat(allEffects.size).isEqualTo(0)
        collectJob.cancel()
    }

    @Test
    fun `verify accept event then reducer return result with state & effect`() = runTest {
        val newState: TestState = mockk()
        val newEffect: TestEffect = mockk()
        every { reducer.reduce(any(), any()) } returns Result(state = newState, effect = newEffect)
        val allEffect = mutableListOf<TestEffect>()
        val job = launch {
            elmViewModel.effects
                .take(1)
                .toList(allEffect)
        }
        advanceUntilIdle()
        assertThat(elmViewModel.states.value).isEqualTo(initialState)

        val testUiEvent: TestEvent = mockk()
        elmViewModel.accept(testUiEvent)

        assertThat(elmViewModel.states.value).isEqualTo(newState)
        job.join()
        verifyAll {
            reducer.reduce(eq(testUiEvent), eq(initialState))
        }
        assertThat(allEffect.size).isEqualTo(1)
        assertThat(allEffect[0]).isEqualTo(newEffect)
    }

    @Test
    fun `verify accept event then reducer return result with state & effect & commands`() =
        runTest {
            val testUiEvent: TestEvent = mockk()
            val secondState: TestState = mockk()
            val thirdState: TestState = mockk()
            val fourthState: TestState = mockk()
            val newEffect: TestEffect = mockk()
            val firstCommand: TestCommand = mockk()
            val secondCommand: TestCommand = mockk()
            val newCommands: List<TestCommand> = listOf(firstCommand, secondCommand)
            val firstInternalEvent: TestEvent = mockk()
            val secondInternalEvent: TestEvent = mockk()

            //region mockk reducer
            every {
                reducer.reduce(testUiEvent, initialState)
            } returns Result(
                state = secondState,
                effect = newEffect,
                commands = newCommands
            )
            every { reducer.reduce(firstInternalEvent, secondState) } returns Result(thirdState)
            every { reducer.reduce(secondInternalEvent, thirdState) } returns Result(fourthState)
            //endregion

            //region mockk actor
            coEvery { actor.execute(firstCommand) } returns firstInternalEvent
            coEvery { actor.execute(secondCommand) } returns secondInternalEvent
            //endregion

            val allStates = mutableListOf<TestState>()
            val collectStatesJob = launch(
                //Используем именно его, а не StandardTestDispatcher(),
                //т.к. нужно запустить корутину, которая будет возобновляться немедленно, без
                //прохождения диспетчеризации
                //TODO на самом деле не до конца понятно, почему не работает с StandardTestDispatcher
                UnconfinedTestDispatcher()
            ) {
                elmViewModel.states
                    .take(4)
                    .toList(allStates)
            }
            val allEffects = mutableListOf<TestEffect>()
            val collectEffectsJob = launch {
                elmViewModel.effects
                    .take(1)
                    .toList(allEffects)
            }
            advanceUntilIdle()

            elmViewModel.accept(testUiEvent)

            collectEffectsJob.join()
            collectStatesJob.join()

            assertThat(allStates).isEqualTo(
                listOf(
                    initialState,
                    secondState,
                    thirdState,
                    fourthState
                )
            )
            assertThat(allEffects[0]).isEqualTo(newEffect)
            coVerifyAll {
                reducer.reduce(eq(testUiEvent), eq(initialState))
                actor.execute(eq(firstCommand))
                reducer.reduce(eq(firstInternalEvent), eq(secondState))
                actor.execute(eq(secondCommand))
                reducer.reduce(eq(secondInternalEvent), eq(thirdState))
            }
        }
    //endregion

    interface TestEvent
    interface TestState
    interface TestEffect
    interface TestCommand
}