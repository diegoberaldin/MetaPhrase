package com.github.diegoberaldin.metaphrase.domain.formats.properties

import com.github.diegoberaldin.metaphrase.domain.project.data.SegmentModel

interface ParsePropertiesUseCase {
    suspend operator fun invoke(path: String): List<SegmentModel>
}
