package com.github.diegoberaldin.metaphrase.domain.tm.persistence.entities

import org.jetbrains.exposed.dao.id.IntIdTable

/**
 * Memory entry entity.
 */
object MemoryEntryEntity : IntIdTable() {
    /**
     * Origin of the entry (project name or TMX file name)
     */
    val origin = mediumText("origin")

    /**
     * Message key or translation unit ID (equivalent of `tuid` attribute in the TMX)
     */
    val identifier = mediumText("identifier")
}
