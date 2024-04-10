@file:OptIn(DelicateCoroutinesApi::class)

package core.data.misc.extensions

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.shareIn
import java.util.concurrent.ConcurrentHashMap
import kotlin.coroutines.coroutineContext

private val globalScope = GlobalScope
private val jobs = ConcurrentHashMap<String, Deferred<Any>>()

fun <T> globalSharedFlow(stopTimeoutMillis: Long = 0, onInit: suspend FlowCollector<T>.() -> Unit = {}): Flow<T> {
    return flow { onInit() }.shareIn(globalScope, SharingStarted.WhileSubscribed(stopTimeoutMillis))
}

fun globalAsync(id: String, block: suspend CoroutineScope.() -> Unit) {
    jobs[id]?.cancel()
    val job = globalScope.async { runCatching { block.invoke(this) } }
    job.invokeOnCompletion { jobs.remove(id, job) }
    jobs[id] = job
}

suspend fun <T> globalAsync(block: suspend CoroutineScope.() -> T): Deferred<Result<T>> {
    return globalScope.async(coroutineContext) { runCatching { block.invoke(this) } }
}