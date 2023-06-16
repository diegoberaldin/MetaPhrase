package com.github.diegoberaldin.metaphrase.domain.formats.ios

import com.github.diegoberaldin.metaphrase.domain.project.data.SegmentModel

interface ParseIosResourcesUseCase {
    suspend operator fun invoke(path: String): List<SegmentModel>
}

