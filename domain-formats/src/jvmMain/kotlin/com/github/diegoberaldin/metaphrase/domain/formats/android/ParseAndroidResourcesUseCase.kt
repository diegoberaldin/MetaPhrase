package com.github.diegoberaldin.metaphrase.domain.formats.android

import com.github.diegoberaldin.metaphrase.domain.project.data.SegmentModel

interface ParseAndroidResourcesUseCase {
    suspend operator fun invoke(path: String): List<SegmentModel>
}
