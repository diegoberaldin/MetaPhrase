package com.github.diegoberaldin.metaphrase.domain.formats.json

import com.github.diegoberaldin.metaphrase.domain.project.data.SegmentModel

interface ParseJsonUseCase {
    suspend operator fun invoke(path: String): List<SegmentModel>
}
