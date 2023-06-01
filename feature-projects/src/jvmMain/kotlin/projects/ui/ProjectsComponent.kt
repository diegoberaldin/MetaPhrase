package projects.ui

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import data.LanguageModel
import data.ProjectModel
import data.ResourceFileType
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
    fun importTmx(path: String)

    sealed interface Config : Parcelable {
        @Parcelize
        object List : Config

        @Parcelize
        data class Detail(val projectId: Int) : Config
    }
}
