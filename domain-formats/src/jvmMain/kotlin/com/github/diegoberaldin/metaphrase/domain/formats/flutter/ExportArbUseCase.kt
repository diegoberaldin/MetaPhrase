package com.github.diegoberaldin.metaphrase.domain.formats.flutter

import com.github.diegoberaldin.metaphrase.domain.project.data.SegmentModel

interface ExportArbUseCase {
    suspend operator fun invoke(segments: List<SegmentModel>, path: String)
}
