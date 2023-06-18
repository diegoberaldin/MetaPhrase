import java.io.InputStream

object LocalizationResourceLoader {
    fun loadAsStream(lang: String): InputStream? {
        val path = getPathForL10nResource(lang)
        return LocalizationResourceLoader::class.java.getResourceAsStream(path)
    }

    private fun getPathForL10nResource(lang: String) = "l10n/$lang/strings.xml"
}
