package panelglossary.ui

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.onClick
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import common.ui.theme.Spacing
import common.utils.toLocalPixel

@OptIn(ExperimentalFoundationApi::class, ExperimentalComposeUiApi::class)
@Composable
internal fun GlossaryAddButton(
    backgroundColor: Color = Color(0xFF666666),
    onAddTerm: (String) -> Unit,
) {
    var expanded by remember { mutableStateOf(false) }
    val expandedWith = 200.dp.toLocalPixel()
    val baseSize = 26.dp
    val collapsedWidth = baseSize.toLocalPixel()
    val width by animateFloatAsState(targetValue = if (expanded) expandedWith else collapsedWidth)
    val focusRequester = remember { FocusRequester() }
    LaunchedEffect(expanded) {
        if (expanded) {
            focusRequester.requestFocus()
        }
    }

    Box(
        modifier = Modifier.height(baseSize)
            .width((width / LocalDensity.current.density).dp)
            .background(color = backgroundColor, shape = RoundedCornerShape(baseSize / 2)),
    ) {
        val buttonModifier = Modifier.size(baseSize).padding(5.dp)
        if (!expanded) {
            Box(
                modifier = buttonModifier
                    .align(Alignment.Center)
                    .onClick {
                        expanded = true
                    },
                contentAlignment = Alignment.Center,
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = null,
                )
            }
        } else {
            var text by remember { mutableStateOf("") }
            BasicTextField(
                modifier = Modifier.padding(horizontal = Spacing.s)
                    .align(Alignment.CenterStart)
                    .focusRequester(focusRequester).onKeyEvent {
                        when (it.key) {
                            Key.Escape -> {
                                expanded = false
                                true
                            }

                            else -> false
                        }
                    },
                maxLines = 1,
                textStyle = MaterialTheme.typography.caption.copy(color = Color.White),
                cursorBrush = SolidColor(Color.White),
                value = text,
                onValueChange = {
                    text = it
                },
            )
            Box(
                modifier = buttonModifier
                    .align(Alignment.CenterEnd)
                    .onClick {
                        if (text.isNotEmpty()) {
                            onAddTerm(text)
                            text = ""
                        }
                        expanded = false
                    },
                contentAlignment = Alignment.Center,
            ) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = null,
                )
            }
        }
    }
}
