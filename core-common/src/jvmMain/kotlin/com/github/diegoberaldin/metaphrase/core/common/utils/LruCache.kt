package com.github.diegoberaldin.metaphrase.core.common.utils

/**
 * A simple implementation of an LRU cache.
 *
 * @param T type of the items to save
 * @property size max size of the cache
 * @constructor Create [LruCache]
 */
class LruCache<T>(private val size: Int) {
    private var keyList = listOf<String>()
    private val map = mutableMapOf<String, T>()

    operator fun set(key: String, value: T) {
        val index = keyList.indexOf(key)
        if (index >= 0) {
            // moves at the beginning
            keyList = listOf(key) + keyList.filter { it != key }
            map[key] = value
        } else {
            if (keyList.size >= size) {
                val keyToRemove = keyList.last()
                keyList = keyList.dropLast(1)
                map.remove(keyToRemove)
            }
            keyList = listOf(key) + keyList
            map[key] = value
        }
    }

    operator fun get(key: String): T? {
        return map[key]
    }
}
