package com.github.diegoberaldin.metaphrase.core.common.keystore

/**
 * Secondary storage in the form of a key store (persistence across application restarts).
 */
interface TemporaryKeyStore {
    /**
     * Save a boolean value in the keystore under a given key.
     *
     * @param key Key
     * @param value Value
     */
    suspend fun save(key: String, value: Boolean)

    /**
     * Retrieve a boolean value from the key store given its key.
     *
     * @param key Key
     * @param default Default value
     * @return value saved in the keystore or the default one
     */
    suspend fun get(key: String, default: Boolean): Boolean

    /**
     * Save a string value in the keystore under a given key.
     *
     * @param key Key
     * @param value Value
     */
    suspend fun save(key: String, value: String)

    /**
     * Retrieve a string value from the key store given its key.
     *
     * @param key Key
     * @param default Default value
     * @return value saved in the keystore or the default one
     */
    suspend fun get(key: String, default: String): String

    /**
     * Save an integer value in the keystore under a given key.
     *
     * @param key Key
     * @param value Value
     */
    suspend fun save(key: String, value: Int)

    /**
     * Retrieve an integer value from the key store given its key.
     *
     * @param key Key
     * @param default Default value
     * @return value saved in the keystore or the default one
     */
    suspend fun get(key: String, default: Int): Int

    /**
     * Save a floating point value in the keystore under a given key.
     *
     * @param key Key
     * @param value Value
     */
    suspend fun save(key: String, value: Float)

    /**
     * Retrieve a floating point value from the key store given its key.
     *
     * @param key Key
     * @param default Default value
     * @return value saved in the keystore or the default one
     */
    suspend fun get(key: String, default: Float): Float

    /**
     * Save a floating point (double precision) value in the keystore under a given key.
     *
     * @param key Key
     * @param value Value
     */
    suspend fun save(key: String, value: Double)

    /**
     * Retrieve a floating point (double precision) value from the key store given its key.
     *
     * @param key Key
     * @param default Default value
     * @return value saved in the keystore or the default one
     */
    suspend fun get(key: String, default: Double): Double
}
