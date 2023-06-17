package com.github.diegoberaldin.metaphrase.domain.formats.flutter

import com.github.diegoberaldin.metaphrase.domain.project.data.SegmentModel

interface ParseArbUseCase {
    suspend operator fun invoke(path: String): List<SegmentModel>
}
