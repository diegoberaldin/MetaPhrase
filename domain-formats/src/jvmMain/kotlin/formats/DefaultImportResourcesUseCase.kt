package formats

import formats.android.ParseAndroidResourcesUseCase
import formats.ios.ParseIosResourcesUseCase
import formats.po.ParsePoUseCase
import formats.resx.ParseResxUseCase
import projectdata.ResourceFileType
import projectdata.SegmentModel

internal class DefaultImportResourcesUseCase(
    private val parseAndroid: ParseAndroidResourcesUseCase,
    private val parseIos: ParseIosResourcesUseCase,
    private val parseResx: ParseResxUseCase,
    private val parsePo: ParsePoUseCase,
) : ImportResourcesUseCase {
    override suspend fun invoke(path: String, type: ResourceFileType): List<SegmentModel> {
        return when (type) {
            ResourceFileType.ANDROID_XML -> parseAndroid(path)
            ResourceFileType.IOS_STRINGS -> parseIos(path)
            ResourceFileType.RESX -> parseResx(path)
            ResourceFileType.PO -> parsePo(path)
            else -> emptyList()
        }
    }
}
