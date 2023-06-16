package com.github.diegoberaldin.metaphrase.domain.formats

import com.github.diegoberaldin.metaphrase.domain.formats.android.ParseAndroidResourcesUseCase
import com.github.diegoberaldin.metaphrase.domain.formats.ios.ParseIosResourcesUseCase
import com.github.diegoberaldin.metaphrase.domain.formats.po.ParsePoUseCase
import com.github.diegoberaldin.metaphrase.domain.formats.resx.ParseResxUseCase
import com.github.diegoberaldin.metaphrase.domain.project.data.ResourceFileType
import com.github.diegoberaldin.metaphrase.domain.project.data.SegmentModel

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
