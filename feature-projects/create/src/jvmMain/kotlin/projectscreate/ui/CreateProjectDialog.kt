package projectscreate.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.onClick
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.rememberWindowState
import common.ui.components.CustomTextField
import common.ui.components.CustomTooltipArea
import common.ui.theme.MetaPhraseTheme
import common.ui.theme.Spacing
import localized

@OptIn(ExperimentalFoundationApi::class, ExperimentalComposeUiApi::class)
@Composable
fun CreateProjectDialog(
    component: CreateProjectComponent,
    onClose: () -> Unit,
) {
    MetaPhraseTheme {
        Window(
            title = "dialog_title_create_project".localized(),
            state = rememberWindowState(width = Dp.Unspecified, height = Dp.Unspecified),
            resizable = false,
            onCloseRequest = {
                onClose()
            },
        ) {
            Surface(
                modifier = Modifier.size(600.dp, 400.dp).background(MaterialTheme.colors.background),
            ) {
                val uiState by component.uiState.collectAsState()
                val languagesUiState by component.languagesUiState.collectAsState()

                Column(
                    modifier = Modifier.padding(vertical = Spacing.s, horizontal = Spacing.lHalf),
                ) {
                    CustomTextField(
                        modifier = Modifier.fillMaxWidth().height(50.dp),
                        label = "create_project_field_name".localized(),
                        value = uiState.name,
                        onValueChange = {
                            component.setName(it)
                        },
                    )
                    Text(
                        text = uiState.nameError,
                        color = MaterialTheme.colors.error,
                        style = MaterialTheme.typography.caption,
                    )
                    Spacer(modifier = Modifier.height(Spacing.xs))
                    Row {
                        Text(
                            text = "create_project_languages".localized(),
                            color = MaterialTheme.colors.onBackground,
                            style = MaterialTheme.typography.caption,
                        )
                        Spacer(modifier = Modifier.weight(1f))
                        var languageDropdownExpanded by remember {
                            mutableStateOf(false)
                        }
                        Box {
                            CustomTooltipArea(
                                text = "tooltip_add",
                            ) {
                                Icon(
                                    modifier = Modifier.size(24.dp)
                                        .onClick {
                                            languageDropdownExpanded = true
                                        },
                                    imageVector = Icons.Default.AddCircle,
                                    tint = MaterialTheme.colors.primary,
                                    contentDescription = null,
                                )
                            }
                            SelectLanguageDropDown(
                                expanded = languageDropdownExpanded,
                                languages = languagesUiState.availableLanguages,
                                onDismiss = {
                                    languageDropdownExpanded = false
                                },
                                onSelected = {
                                    component.addLanguage(it)
                                    languageDropdownExpanded = false
                                },
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(Spacing.xs))
                    Text(
                        text = "create_project_languages_subtitle".localized(),
                        color = MaterialTheme.colors.onBackground,
                        style = MaterialTheme.typography.caption,
                    )
                    Spacer(modifier = Modifier.height(Spacing.s))
                    LazyColumn(
                        modifier = Modifier.weight(1f),
                        verticalArrangement = Arrangement.spacedBy(Spacing.xs),
                    ) {
                        items(languagesUiState.languages) { language ->
                            LanguageCell(
                                language = language,
                                onSelected = {
                                    component.setBaseLanguage(language)
                                },
                                onDeleteClicked = {
                                    component.removeLanguage(language)
                                },
                            )
                        }
                    }
                    Text(
                        text = languagesUiState.languagesError,
                        color = MaterialTheme.colors.error,
                        style = MaterialTheme.typography.caption,
                    )
                    Row(
                        modifier = Modifier.padding(Spacing.s),
                        horizontalArrangement = Arrangement.spacedBy(Spacing.xs),
                    ) {
                        Spacer(modifier = Modifier.weight(1f))
                        Button(
                            modifier = Modifier.heightIn(max = 25.dp),
                            contentPadding = PaddingValues(0.dp),
                            onClick = {
                                onClose()
                            },
                        ) {
                            Text(text = "button_cancel".localized(), style = MaterialTheme.typography.button)
                        }
                        Button(
                            modifier = Modifier.heightIn(max = 25.dp),
                            contentPadding = PaddingValues(0.dp),
                            onClick = {
                                component.submit()
                            },
                        ) {
                            Text(text = "button_ok".localized(), style = MaterialTheme.typography.button)
                        }
                    }
                }
            }
        }
    }
}
