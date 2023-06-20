package com.github.diegoberaldin.metaphrase.core.common.utils

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
                keyList = keyList.dropLast(1)
            }
            keyList = listOf(key) + keyList
            map[key] = value
        }
    }

    operator fun get(key: String): T? {
        return map[key]
    }
}
