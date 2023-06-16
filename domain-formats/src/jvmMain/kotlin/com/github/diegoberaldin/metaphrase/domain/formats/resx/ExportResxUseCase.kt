package com.github.diegoberaldin.metaphrase.domain.formats.resx

import com.github.diegoberaldin.metaphrase.domain.project.data.SegmentModel

interface ExportResxUseCase {
    suspend operator fun invoke(segments: List<SegmentModel>, path: String)
}
