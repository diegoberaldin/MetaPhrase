package com.github.diegoberaldin.metaphrase.feature.projects.presentation

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import com.github.diegoberaldin.metaphrase.domain.language.data.LanguageModel
import com.github.diegoberaldin.metaphrase.domain.project.data.ProjectModel
import com.github.diegoberaldin.metaphrase.domain.project.data.ResourceFileType
import kotlinx.coroutines.flow.StateFlow

interface ProjectsComponent {
    val activeProject: StateFlow<ProjectModel?>
    val childStack: Value<ChildStack<Config, *>>
    val isEditing: StateFlow<Boolean>
    val currentLanguage: StateFlow<LanguageModel?>

    fun open(projectId: Int)
    fun closeCurrentProject()
    fun import(path: String, type: ResourceFileType)
    fun export(path: String, type: ResourceFileType)
    fun moveToPrevious()
    fun moveToNext()
    fun endEditing()
    fun copyBase()
    fun addSegment()
    fun deleteSegment()
    fun exportTmx(path: String)
    fun validatePlaceholders()
    fun insertBestMatch()
    fun globalSpellcheck()
    fun syncWithTm()

    sealed interface Config : Parcelable {
        @Parcelize
        object List : Config

        @Parcelize
        data class Detail(val projectId: Int) : Config
    }
}
