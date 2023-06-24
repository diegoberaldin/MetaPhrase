package com.github.diegoberaldin.metaphrase.domain.project.data

/**
 * Project for the recent project screen. An item in this list has a name and a path in the filesystem
 * to load the project.
 *
 * @property id item ID
 * @property path path on disk
 * @property name project name
 * @constructor Create [RecentProjectModel]
 */
data class RecentProjectModel(
    val id: Int = 0,
    val path: String = "",
    val name: String = ""
)
