package com.github.diegoberaldin.metaphrase.core.common.ui.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.onClick
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowDropDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.onPointerEvent
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import com.github.diegoberaldin.metaphrase.core.common.ui.theme.Spacing

/**
 * Custom spinner for drop-down selection.
 *
 * @param values Set of available values
 * @param current Current value
 * @param modifier Modifier
 * @param size Size of the element and of the drop-down menu
 * @param onValueChanged On value changed callback
 * @param valueColor Value text color
 * @return
 */
@OptIn(ExperimentalComposeUiApi::class, ExperimentalFoundationApi::class)
@Composable
fun CustomSpinner(
    values: List<String>,
    current: String?,
    modifier: Modifier = Modifier,
    size: DpSize = DpSize(width = 150.dp, height = 20.dp),
    onValueChanged: ((Int) -> Unit)? = null,
    valueColor: Color = MaterialTheme.colorScheme.onBackground,
) {
    var expanded by remember {
        mutableStateOf(false)
    }

    Box(
        modifier = modifier.size(size),
    ) {
        BasicTextField(
            modifier = Modifier.matchParentSize(),
            value = current ?: "",
            textStyle = MaterialTheme.typography.labelSmall.copy(color = valueColor),
            readOnly = true,
            onValueChange = {},
            decorationBox = { innerTextField ->
                Box(
                    modifier = Modifier.padding(start = Spacing.s),
                    contentAlignment = Alignment.CenterStart,
                ) {
                    Icon(
                        modifier = Modifier.align(Alignment.CenterEnd),
                        imageVector = Icons.Outlined.ArrowDropDown,
                        contentDescription = null,
                    )
                    innerTextField()
                }
            },
        )
        DropdownMenu(
            modifier = Modifier.width(size.width)
                .background(MaterialTheme.colorScheme.background)
                .border(
                    width = Dp.Hairline,
                    color = MaterialTheme.colorScheme.background,
                    shape = RoundedCornerShape(4.dp),
                ),
            expanded = expanded,
            onDismissRequest = {
                expanded = false
            },
        ) {
            var hoveredValue by remember {
                mutableStateOf<String?>(null)
            }
            values.forEachIndexed { idx, value ->
                DropdownMenuItem(
                    modifier = Modifier
                        .background(color = if (value == hoveredValue) MaterialTheme.colorScheme.secondary else Color.Transparent)
                        .fillMaxWidth().height(24.dp)
                        .onPointerEvent(PointerEventType.Enter) { hoveredValue = value }
                        .onPointerEvent(PointerEventType.Exit) { hoveredValue = null }
                        .padding(horizontal = Spacing.xs, vertical = Spacing.xxs),
                    onClick = {
                        onValueChanged?.invoke(idx)
                        expanded = false
                    },
                    text = {
                        Text(
                            text = value,
                            style = MaterialTheme.typography.labelSmall,
                            color = if (value == hoveredValue) MaterialTheme.colorScheme.onSecondary else valueColor,
                        )
                    },
                )
            }
        }
        Spacer(
            modifier = Modifier.matchParentSize().onClick {
                if (values.isEmpty()) {
                    return@onClick
                }
                expanded = !expanded
            },
        )
    }
}
