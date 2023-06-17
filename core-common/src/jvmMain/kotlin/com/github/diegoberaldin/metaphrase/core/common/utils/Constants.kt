package com.github.diegoberaldin.metaphrase.core.common.utils

object Constants {
    val PlaceholderRegex = Regex("%(?<pos>:\\d+\\\$)?[dfsu]")
    val NamedPlaceholderRegex = Regex("(\\{\\{.*?}})|(\\{.*?})")
}
