package com.github.diegoberaldin.metaphrase.domain.formats.ios

import com.github.diegoberaldin.metaphrase.domain.project.data.SegmentModel

interface ExportIosResourcesUseCase {
    suspend operator fun invoke(segments: List<SegmentModel>, path: String)
}
