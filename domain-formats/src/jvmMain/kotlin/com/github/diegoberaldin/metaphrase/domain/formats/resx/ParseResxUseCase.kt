package com.github.diegoberaldin.metaphrase.domain.formats.resx

import com.github.diegoberaldin.metaphrase.domain.project.data.SegmentModel

interface ParseResxUseCase {
    suspend operator fun invoke(path: String): List<SegmentModel>
}
