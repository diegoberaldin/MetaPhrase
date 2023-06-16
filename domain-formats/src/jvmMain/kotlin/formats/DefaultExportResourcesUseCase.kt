package formats

import formats.android.ExportAndroidResourcesUseCase
import formats.ios.ExportIosResourcesUseCase
import formats.po.ExportPoUseCase
import formats.resx.ExportResxUseCase
import projectdata.ResourceFileType
import projectdata.SegmentModel

internal class DefaultExportResourcesUseCase(
    private val exportAndroid: ExportAndroidResourcesUseCase,
    private val exportIos: ExportIosResourcesUseCase,
    private val exportResx: ExportResxUseCase,
    private val exportPo: ExportPoUseCase,
) : ExportResourcesUseCase {
    override suspend fun invoke(segments: List<SegmentModel>, path: String, lang: String, type: ResourceFileType) {
        when (type) {
            ResourceFileType.ANDROID_XML -> {
                exportAndroid(segments = segments, path = path)
            }

            ResourceFileType.IOS_STRINGS -> {
                exportIos(segments = segments, path = path)
            }

            ResourceFileType.RESX -> {
                exportResx(segments = segments, path = path)
            }

            ResourceFileType.PO -> {
                exportPo(segments = segments, path = path, lang = lang)
            }

            else -> Unit
        }
    }
}
