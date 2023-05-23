package data

import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize

@Parcelize
data class InvalidPlaceholderReferenceModel(
    val key: String = "",
    val missingPlaceholders: List<String> = emptyList(),
    val extraPlaceholders: List<String> = emptyList()
) : Parcelable