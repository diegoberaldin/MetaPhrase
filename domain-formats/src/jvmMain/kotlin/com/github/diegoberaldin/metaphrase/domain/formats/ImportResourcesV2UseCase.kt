package com.github.diegoberaldin.metaphrase.domain.formats

import com.github.diegoberaldin.metaphrase.domain.project.data.ResourceFileType

/**
 * Contract for the import resources "all-in-one" use case.
 */
interface ImportResourcesV2UseCase {
    /**
     * Import the content of a series of resource files into a project.
     *
     * @param projectId Project ID
     * @param paths Map from language code to source path
     * @param type Resource type
     */
    suspend operator fun invoke(
        projectId: Int,
        paths: Map<String, String>,
        type: ResourceFileType,
    )
}
