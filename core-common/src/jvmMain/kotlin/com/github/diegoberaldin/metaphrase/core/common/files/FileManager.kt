package com.github.diegoberaldin.metaphrase.core.common.files

/**
 * Utility to access files on disk.
 */
interface FileManager {
    /**
     * Get the path for the given components in a private application space on disk.
     * The output will vary depending on the platform, but it can be assumed to be writable and readable.
     *
     * @param components Path components
     * @return the local file path
     */
    fun getFilePath(vararg components: String): String
}
