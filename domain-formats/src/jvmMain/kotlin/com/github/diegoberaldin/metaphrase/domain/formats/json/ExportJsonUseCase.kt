package com.github.diegoberaldin.metaphrase.domain.formats.json

import com.github.diegoberaldin.metaphrase.domain.project.data.SegmentModel

interface ExportJsonUseCase {
    suspend operator fun invoke(segments: List<SegmentModel>, path: String)
}
