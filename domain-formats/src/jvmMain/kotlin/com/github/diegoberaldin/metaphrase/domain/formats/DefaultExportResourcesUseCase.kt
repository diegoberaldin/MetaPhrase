package com.github.diegoberaldin.metaphrase.domain.formats

import com.github.diegoberaldin.metaphrase.domain.formats.android.ExportAndroidResourcesUseCase
import com.github.diegoberaldin.metaphrase.domain.formats.ios.ExportIosResourcesUseCase
import com.github.diegoberaldin.metaphrase.domain.formats.json.ExportJsonUseCase
import com.github.diegoberaldin.metaphrase.domain.formats.po.ExportPoUseCase
import com.github.diegoberaldin.metaphrase.domain.formats.resx.ExportResxUseCase
import com.github.diegoberaldin.metaphrase.domain.project.data.ResourceFileType
import com.github.diegoberaldin.metaphrase.domain.project.data.SegmentModel

internal class DefaultExportResourcesUseCase(
    private val exportAndroid: ExportAndroidResourcesUseCase,
    private val exportIos: ExportIosResourcesUseCase,
    private val exportResx: ExportResxUseCase,
    private val exportPo: ExportPoUseCase,
    private val exportJson: ExportJsonUseCase,
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

            ResourceFileType.JSON -> {
                exportJson(segments = segments, path = path)
            }

            else -> Unit
        }
    }
}
