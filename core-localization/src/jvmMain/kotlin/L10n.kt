import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.isActive

object L10n {

    private val default = DefaultLocalization()

    val currentLanguage = channelFlow<String> {
        while (true) {
            if (!isActive) {
                break
            }
            trySend(get("lang"))
            delay(1000)
        }
    }

    fun setLanguage(lang: String) {
        default.setLanguage(lang)
    }

    fun get(key: String): String = default.get(key)
}

fun String.localized(): String {
    return L10n.get(this)
}
