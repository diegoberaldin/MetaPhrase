package com.github.diegoberaldin.metaphrase.domain.formats.properties

import com.github.diegoberaldin.metaphrase.domain.project.data.SegmentModel

interface ExportPropertiesUseCase {
    suspend operator fun invoke(segments: List<SegmentModel>, path: String)
}
