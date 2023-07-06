package com.github.diegoberaldin.feature.main.settings.dialog.login.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.onClick
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.rememberWindowState
import com.github.diegoberaldin.feature.main.settings.dialog.login.presentation.LoginComponent
import com.github.diegoberaldin.metaphrase.core.common.ui.components.CustomTextField
import com.github.diegoberaldin.metaphrase.core.common.ui.components.CustomTooltipArea
import com.github.diegoberaldin.metaphrase.core.common.ui.theme.Gray192330
import com.github.diegoberaldin.metaphrase.core.common.ui.theme.MetaPhraseTheme
import com.github.diegoberaldin.metaphrase.core.common.ui.theme.Spacing
import com.github.diegoberaldin.metaphrase.core.localization.localized
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

/**
 * UI content of the login dialog.
 *
 * @param component component
 * @param onClose on close callback
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun LoginDialog(
    component: LoginComponent,
    onClose: (String?, String?) -> Unit,
) {
    val uiState by component.uiState.collectAsState()

    LaunchedEffect(component) {
        component.effects.onEach {
            if (it is LoginComponent.Effect.Done) {
                onClose(it.username, it.password)
            }
        }.launchIn(this)
    }

    MetaPhraseTheme {
        Window(
            title = "dialog_title_login".localized(),
            state = rememberWindowState(width = Dp.Unspecified, height = Dp.Unspecified),
            resizable = false,
            onCloseRequest = {
                onClose(null, null)
            },
        ) {
            Surface(
                modifier = Modifier.size(400.dp, 200.dp),
                color = MaterialTheme.colors.background,
            ) {
                Column(
                    modifier = Modifier.fillMaxSize()
                        .padding(vertical = Spacing.s, horizontal = Spacing.lHalf),
                ) {
                    CustomTextField(
                        modifier = Modifier.fillMaxWidth().height(50.dp),
                        label = "field_username".localized(),
                        value = uiState.username,
                        singleLine = true,
                        onValueChange = {
                            component.reduce(LoginComponent.ViewIntent.SetUsername(value = it))
                        },
                    )
                    Text(
                        modifier = Modifier.padding(top = Spacing.xxs, start = Spacing.xxs),
                        text = uiState.usernameError,
                        color = MaterialTheme.colors.error,
                        style = MaterialTheme.typography.caption,
                    )
                    Spacer(modifier = Modifier.height(Spacing.xs))
                    var passwordVisible by remember {
                        mutableStateOf(false)
                    }
                    CustomTextField(
                        modifier = Modifier.fillMaxWidth().height(50.dp),
                        label = "field_password".localized(),
                        secure = !passwordVisible,
                        value = uiState.password,
                        singleLine = true,
                        onValueChange = {
                            component.reduce(LoginComponent.ViewIntent.SetPassword(value = it))
                        },
                        endButton = {
                            CustomTooltipArea(
                                text = if (passwordVisible) "tooltip_hide_password".localized() else "tooltip_show_password".localized(),
                            ) {
                                Icon(
                                    modifier = Modifier.onClick {
                                        passwordVisible = !passwordVisible
                                    },
                                    tint = Gray192330,
                                    imageVector = if (passwordVisible) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                                    contentDescription = null,
                                )
                            }
                        },
                    )
                    Text(
                        modifier = Modifier.padding(top = Spacing.xxs, start = Spacing.xxs),
                        text = uiState.passwordError,
                        color = MaterialTheme.colors.error,
                        style = MaterialTheme.typography.caption,
                    )
                    Spacer(modifier = Modifier.height(Spacing.xs))
                    Row(
                        modifier = Modifier.padding(Spacing.s),
                        horizontalArrangement = Arrangement.spacedBy(Spacing.xs),
                    ) {
                        Spacer(modifier = Modifier.weight(1f))
                        Button(
                            modifier = Modifier.heightIn(max = 25.dp),
                            contentPadding = PaddingValues(0.dp),
                            onClick = {
                                onClose(null, null)
                            },
                        ) {
                            Text(text = "button_cancel".localized(), style = MaterialTheme.typography.button)
                        }
                        Button(
                            modifier = Modifier.heightIn(max = 25.dp),
                            contentPadding = PaddingValues(0.dp),
                            onClick = {
                                component.reduce(LoginComponent.ViewIntent.Submit)
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
