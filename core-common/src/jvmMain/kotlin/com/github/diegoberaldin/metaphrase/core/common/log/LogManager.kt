package com.github.diegoberaldin.metaphrase.core.common.log

/**
 * Abstract log manager.
 */
interface LogManager {
    /**
     * Log a message with the TRACE level.
     *
     * @param message Log message
     */
    fun trace(message: String)

    /**
     * Log a message with the DEBUG level.
     *
     * @param message Log message
     */
    fun debug(message: String)

    /**
     * Log a message with the INFO level.
     *
     * @param message Log message
     */
    fun info(message: String)

    /**
     * Log a message with the WARNING level.
     *
     * @param message Log message
     */
    fun warn(message: String)

    /**
     * Log a message with the ERROR level.
     *
     * @param message Log message
     */
    fun error(message: String)

    /**
     * Log an exception.
     *
     * @param message Log message
     * @param cause Root cause
     */
    fun exception(message: String, cause: Throwable)
}
