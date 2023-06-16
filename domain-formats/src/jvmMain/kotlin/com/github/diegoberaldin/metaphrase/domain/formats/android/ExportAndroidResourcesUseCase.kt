package com.github.diegoberaldin.metaphrase.domain.formats.android

import com.github.diegoberaldin.metaphrase.domain.project.data.SegmentModel

interface ExportAndroidResourcesUseCase {
    suspend operator fun invoke(segments: List<SegmentModel>, path: String)
}
