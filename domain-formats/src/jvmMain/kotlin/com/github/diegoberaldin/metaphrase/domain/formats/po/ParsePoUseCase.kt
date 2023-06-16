package com.github.diegoberaldin.metaphrase.domain.formats.po

import com.github.diegoberaldin.metaphrase.domain.project.data.SegmentModel

interface ParsePoUseCase {
    suspend operator fun invoke(path: String): List<SegmentModel>
}
