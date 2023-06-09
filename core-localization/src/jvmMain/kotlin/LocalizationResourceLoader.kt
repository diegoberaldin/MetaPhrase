import java.io.InputStream

internal object LocalizationResourceLoader {
    fun loadAsStream(lang: String): InputStream? {
        val path = getPathForL10nResource()
        return LocalizationResourceLoader::class.java.getResourceAsStream(path)
    }

    private fun getPathForL10nResource() = "MetaPhrase.tmx"
}
