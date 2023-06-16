package com.github.diegoberaldin.metaphrase.domain.formats

import com.github.diegoberaldin.metaphrase.domain.project.data.ResourceFileType
import com.github.diegoberaldin.metaphrase.domain.project.data.SegmentModel

interface ExportResourcesUseCase {
    suspend operator fun invoke(segments: List<SegmentModel>, path: String, lang: String, type: ResourceFileType)
}
