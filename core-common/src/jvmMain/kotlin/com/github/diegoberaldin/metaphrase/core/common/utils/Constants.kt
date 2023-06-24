package com.github.diegoberaldin.metaphrase.core.common.utils

/**
 * Global constants.
 */
object Constants {
    /**
     * C-style placeholder regex
     */
    val PlaceholderRegex = Regex("%(?<pos>:\\d+\\\$)?[dfsu]")

    /**
     * ngx-translate placeholder regex
     */
    val NamedPlaceholderRegex = Regex("(\\{\\{.*?}})|(\\{.*?})")
}
