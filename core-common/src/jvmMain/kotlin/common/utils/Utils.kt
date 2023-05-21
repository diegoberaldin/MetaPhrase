package common.utils

import com.arkivanov.decompose.router.slot.ChildSlot
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import org.koin.java.KoinJavaComponent
import javax.swing.SwingUtilities

fun <T> runOnUiThread(block: () -> T): T {
    if (SwingUtilities.isEventDispatchThread()) {
        return block()
    }

    var error: Throwable? = null
    var result: T? = null

    SwingUtilities.invokeAndWait {
        try {
            result = block()
        } catch (e: Throwable) {
            error = e
        }
    }

    error?.also { throw it }

    @Suppress("UNCHECKED_CAST")
    return result as T
}

inline fun <reified T> getByInjection(): T {
    val res by KoinJavaComponent.inject<T>(T::class.java)
    return res
}

inline fun <reified T> observeChildStack(childStack: Value<ChildStack<*, *>>): Flow<T> = callbackFlow {
    val observer: (ChildStack<*, *>) -> Unit = {
        val child = it.active.instance
        if (T::class.java.isInstance(child)) {
            trySend(child as T)
        }
    }
    childStack.subscribe(observer)
    awaitClose {
        childStack.unsubscribe(observer)
    }
}

inline fun <reified T> observeNullableChildStack(childStack: Value<ChildStack<*, *>>): Flow<T?> = callbackFlow {
    val observer: (ChildStack<*, *>) -> Unit = {
        val child = it.active.instance
        if (T::class.java.isInstance(child)) {
            trySend(child as T)
        } else {
            trySend(null)
        }
    }
    childStack.subscribe(observer)
    awaitClose {
        childStack.unsubscribe(observer)
    }
}

inline fun <reified T> observeChildSlot(childStack: Value<ChildSlot<*, *>>): Flow<T> = callbackFlow {
    val observer: (ChildSlot<*, *>) -> Unit = {
        val child = it.child?.instance
        if (T::class.java.isInstance(child)) {
            trySend(child as T)
        }
    }
    childStack.subscribe(observer)
    awaitClose {
        childStack.unsubscribe(observer)
    }
}

inline fun <reified T> observeNullableChildSlot(childStack: Value<ChildSlot<*, *>>): Flow<T?> = callbackFlow {
    val observer: (ChildSlot<*, *>) -> Unit = {
        val child = it.child?.instance
        if (T::class.java.isInstance(child)) {
            trySend(child as T)
        } else {
            trySend(null)
        }
    }
    childStack.subscribe(observer)
    awaitClose {
        childStack.unsubscribe(observer)
    }
}
