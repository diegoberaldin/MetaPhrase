package com.github.diegoberaldin.metaphrase.feature.translate.panel.memory.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.onClick
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onPreviewKeyEvent
import androidx.compose.ui.input.key.type
import androidx.compose.ui.unit.dp
import com.github.diegoberaldin.metaphrase.core.common.ui.components.CustomSpinner
import com.github.diegoberaldin.metaphrase.core.common.ui.components.CustomTextField
import com.github.diegoberaldin.metaphrase.core.common.ui.theme.SelectedBackground
import com.github.diegoberaldin.metaphrase.core.common.ui.theme.Spacing
import com.github.diegoberaldin.metaphrase.core.localization.localized
import com.github.diegoberaldin.metaphrase.domain.language.data.LanguageModel

/**
 * Top controls for the TM content panel.
 *
 * @param sourceLanguage Source language
 * @param availableSourceLanguages Available source languages
 * @param targetLanguage Target language
 * @param availableTargetLanguages Available target languages
 * @param currentSearch Current search query
 * @param onSourceLanguageSelected On source language selected callback
 * @param onTargetLanguageSelected On target language selected callback
 * @param onSearchChanged On search changed callback
 * @param onSearchFired On search fired callback
 * @return
 */
@OptIn(ExperimentalComposeUiApi::class, ExperimentalFoundationApi::class)
@Composable
fun BrowseMemoryTopControls(
    sourceLanguage: LanguageModel? = null,
    availableSourceLanguages: List<LanguageModel> = emptyList(),
    targetLanguage: LanguageModel? = null,
    availableTargetLanguages: List<LanguageModel> = emptyList(),
    currentSearch: String = "",
    onSourceLanguageSelected: (LanguageModel) -> Unit,
    onTargetLanguageSelected: (LanguageModel) -> Unit,
    onSearchChanged: (String) -> Unit,
    onSearchFired: () -> Unit,
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(Spacing.xs),
    ) {
        val elementHeight = 26.dp
        // spinners
        val spinnerModifier = Modifier.width(140.dp)
            .height(elementHeight)
            .background(color = SelectedBackground, shape = RoundedCornerShape(4.dp))
        CustomSpinner(
            modifier = spinnerModifier,
            valueColor = MaterialTheme.colors.onBackground,
            values = availableSourceLanguages.map { it.name },
            current = sourceLanguage?.name.orEmpty(),
            onValueChanged = {
                val lang = availableSourceLanguages[it]
                onSourceLanguageSelected(lang)
            },
        )
        CustomSpinner(
            modifier = spinnerModifier,
            valueColor = MaterialTheme.colors.onBackground,
            values = availableTargetLanguages.map { it.name },
            current = targetLanguage?.name.orEmpty(),
            onValueChanged = {
                val lang = availableTargetLanguages[it]
                onTargetLanguageSelected(lang)
            },
        )
        // search
        CustomTextField(
            modifier = Modifier.weight(1f).height(elementHeight).onPreviewKeyEvent {
                when {
                    it.type == KeyEventType.KeyDown && it.key == Key.Enter -> {
                        onSearchFired()
                        true
                    }

                    else -> false
                }
            },
            hint = "toolbar_search_placeholder".localized(),
            singleLine = true,
            value = currentSearch,
            backgroundColor = SelectedBackground,
            textColor = MaterialTheme.colors.onBackground,
            onValueChange = {
                onSearchChanged(it)
            },
            endButton = {
                if (currentSearch.isEmpty()) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = null,
                    )
                } else {
                    Icon(
                        modifier = Modifier.onClick {
                            onSearchChanged("")
                            onSearchFired()
                        },
                        imageVector = Icons.Default.Clear,
                        contentDescription = null,
                    )
                }
            },
        )
    }
}
