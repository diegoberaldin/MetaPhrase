package com.github.diegoberaldin.metaphrase.domain.formats

import com.github.diegoberaldin.metaphrase.domain.project.data.ResourceFileType
import com.github.diegoberaldin.metaphrase.domain.project.data.SegmentModel

interface ImportResourcesUseCase {
    suspend operator fun invoke(path: String, type: ResourceFileType): List<SegmentModel>
}
