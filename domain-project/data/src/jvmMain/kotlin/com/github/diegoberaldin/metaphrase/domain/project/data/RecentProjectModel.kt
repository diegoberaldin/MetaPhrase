package com.github.diegoberaldin.metaphrase.domain.project.data

import com.github.diegoberaldin.metaphrase.core.common.utils.lastPathSegment
import com.github.diegoberaldin.metaphrase.core.common.utils.stripExtension

data class RecentProjectModel(
    val id: Int = 0,
    val path: String = "",
) {
    val readableName: String
        get() = path.lastPathSegment().stripExtension()
}
