package com.github.diegoberaldin.metaphrase.domain.formats.po

import com.github.diegoberaldin.metaphrase.domain.project.data.SegmentModel

interface ExportPoUseCase {
    suspend operator fun invoke(segments: List<SegmentModel>, path: String, lang: String)
}
