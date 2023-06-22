package com.github.diegoberaldin.metaphrase.feature.translate.toolbar.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.onClick
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.ArrowCircleDown
import androidx.compose.material.icons.filled.ArrowCircleUp
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.RemoveCircle
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.SwapHorizontalCircle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onPreviewKeyEvent
import androidx.compose.ui.input.key.type
import androidx.compose.ui.unit.dp
import com.github.diegoberaldin.metaphrase.core.common.ui.components.CustomSpinner
import com.github.diegoberaldin.metaphrase.core.common.ui.components.CustomTextField
import com.github.diegoberaldin.metaphrase.core.common.ui.components.CustomTooltipArea
import com.github.diegoberaldin.metaphrase.core.common.ui.theme.SelectedBackground
import com.github.diegoberaldin.metaphrase.core.common.ui.theme.Spacing
import com.github.diegoberaldin.metaphrase.core.localization.localized
import com.github.diegoberaldin.metaphrase.feature.translate.toolbar.presentation.TranslateToolbarComponent
import com.github.diegoberaldin.metaphrase.feature.translate.toolbar.presentation.toReadableString

@OptIn(ExperimentalComposeUiApi::class, ExperimentalFoundationApi::class)
@Composable
fun TranslateToolbar(
    component: TranslateToolbarComponent,
    modifier: Modifier = Modifier,
) {
    val uiState by component.uiState.collectAsState()
    val elementHeight = 26.dp

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(Spacing.xs),
    ) {
        // buttons
        val buttonModifier = Modifier.size(elementHeight).padding(Spacing.xxxs)
        CustomTooltipArea(
            text = "tooltip_move_to_previous".localized(),
        ) {
            Icon(
                modifier = buttonModifier.onClick { component.moveToPrevious() },
                imageVector = Icons.Default.ArrowCircleUp,
                contentDescription = null,
                tint = if (uiState.isEditing) MaterialTheme.colors.primary else Color.Gray,
            )
        }
        CustomTooltipArea(
            text = "tooltip_move_to_next".localized(),
        ) {
            Icon(
                modifier = buttonModifier.onClick { component.moveToNext() },
                imageVector = Icons.Default.ArrowCircleDown,
                contentDescription = null,
                tint = if (uiState.isEditing) MaterialTheme.colors.primary else Color.Gray,
            )
        }
        CustomTooltipArea(
            text = "tooltip_copy_base".localized(),
        ) {
            Icon(
                modifier = buttonModifier.onClick { component.copyBase() },
                imageVector = Icons.Default.SwapHorizontalCircle,
                contentDescription = null,
                tint = if (uiState.currentLanguage?.isBase == false) MaterialTheme.colors.primary else Color.Gray,
            )
        }
        CustomTooltipArea(
            text = "tooltip_add".localized(),
        ) {
            Icon(
                modifier = buttonModifier.onClick { component.addUnit() },
                imageVector = Icons.Default.AddCircle,
                contentDescription = null,
                tint = MaterialTheme.colors.primary,
            )
        }
        CustomTooltipArea(
            text = "tooltip_delete".localized(),
        ) {
            Icon(
                modifier = buttonModifier.onClick { component.removeUnit() },
                imageVector = Icons.Default.RemoveCircle,
                contentDescription = null,
                tint = if (uiState.isEditing) MaterialTheme.colors.primary else Color.Gray,
            )
        }
        CustomTooltipArea(
            text = "tooltip_validate".localized(),
        ) {
            Icon(
                modifier = buttonModifier.onClick { component.validateUnits() },
                imageVector = Icons.Default.CheckCircle,
                contentDescription = null,
                tint = if (uiState.currentLanguage?.isBase == false) MaterialTheme.colors.primary else Color.Gray,
            )
        }

        // spinners
        val spinnerModifier = Modifier.width(140.dp)
            .height(elementHeight)
            .background(color = SelectedBackground, shape = RoundedCornerShape(4.dp))
        CustomSpinner(
            modifier = spinnerModifier,
            valueColor = MaterialTheme.colors.onBackground,
            values = uiState.availableLanguages.map { it.name },
            current = uiState.currentLanguage?.name.orEmpty(),
            onValueChanged = {
                val lang = uiState.availableLanguages[it]
                component.setLanguage(lang)
            },
        )
        CustomSpinner(
            modifier = spinnerModifier,
            valueColor = MaterialTheme.colors.onBackground,
            values = uiState.availableFilters.map { it.toReadableString() },
            current = uiState.currentTypeFilter.toReadableString(),
            onValueChanged = {
                val filter = uiState.availableFilters[it]
                component.setTypeFilter(filter)
            },
        )

        // search
        CustomTextField(
            modifier = Modifier.weight(1f).height(elementHeight).onPreviewKeyEvent {
                when {
                    it.type == KeyEventType.KeyDown && it.key == Key.Enter -> {
                        component.onSearchFired()
                        true
                    }

                    else -> false
                }
            },
            hint = "toolbar_search_placeholder".localized(),
            singleLine = true,
            value = uiState.currentSearch,
            backgroundColor = SelectedBackground,
            textColor = MaterialTheme.colors.onBackground,
            onValueChange = {
                component.setSearch(it)
            },
            endButton = {
                if (uiState.currentSearch.isEmpty()) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = null,
                    )
                } else {
                    Icon(
                        modifier = Modifier.onClick {
                            component.setSearch("")
                            component.onSearchFired()
                        },
                        imageVector = Icons.Default.Clear,
                        contentDescription = null,
                    )
                }
            },
        )
    }
}
