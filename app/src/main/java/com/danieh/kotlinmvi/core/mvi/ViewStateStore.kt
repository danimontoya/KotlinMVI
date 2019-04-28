package com.danieh.kotlinmvi.core.mvi

import androidx.annotation.MainThread
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.*
import kotlin.coroutines.CoroutineContext

class Action<T>(private val f: T.() -> T) {
    operator fun invoke(t: T) = t.f()
}

class ViewStateStore<T : Any>(initialState: T) : CoroutineScope {

    companion object {
        fun <T : Any> test(initialState: T): ViewStateStore<T> {
            return ViewStateStore(initialState)
        }
    }

    val liveData = MutableLiveData<T>().apply {
        value = initialState
    }

    private val job = Job()

    override val coroutineContext: CoroutineContext = job + Dispatchers.IO

    fun observe(owner: LifecycleOwner, observer: (T) -> Unit) =
        liveData.observe(owner, Observer { observer(it!!) })

    @MainThread
    fun dispatchState(state: T) {
        liveData.value = state
    }

    fun dispatchAction(f: suspend (T) -> Action<T>) {
        launch {
            val action = f(state())
            withContext(Dispatchers.Main) {
                dispatchState(action(state()))
            }
        }
    }

    fun dispatchActions(channel: ReceiveChannel<Action<T>>) {
        launch {
            channel.consumeEach { action ->
                withContext(Dispatchers.Main) {
                    dispatchState(action(state()))
                }
            }
        }
    }

    fun state() = liveData.value!!

    fun cancel() = job.cancel()
}

fun <T> produceActions(f: suspend ProducerScope<Action<T>>.() -> Unit): ReceiveChannel<Action<T>> =
    GlobalScope.produce(block = f)

suspend fun <T> ProducerScope<Action<T>>.send(f: T.() -> T) = send(Action(f))

suspend inline fun <T> ReceiveChannel<Action<T>>.states(initialState: T): List<T> {
    return fold(emptyList()) { states, action ->
        states + action(states.lastOrNull() ?: initialState)
    }
}
