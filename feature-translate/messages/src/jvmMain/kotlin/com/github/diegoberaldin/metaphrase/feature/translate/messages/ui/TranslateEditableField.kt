package com.github.diegoberaldin.metaphrase.feature.translate.messages.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextDecoration
import com.github.diegoberaldin.metaphrase.domain.project.data.TranslationUnit
import com.github.diegoberaldin.metaphrase.domain.spellcheck.SpellCheckCorrection

@Composable
fun TranslateEditableField(
    unit: TranslationUnit,
    focusRequester: FocusRequester = remember { FocusRequester() },
    active: Boolean = false,
    updateTextSwitch: Boolean = false,
    enabled: Boolean = true,
    spellingErrors: List<SpellCheckCorrection> = emptyList(),
    onStartEditing: () -> Unit = {},
    onTextChanged: (String) -> Unit = {},
    onAddToGlossary: (String) -> Unit = {},
    onIgnoreWord: (String) -> Unit = {},
) {
    var value by remember(
        key1 = unit.segment.id,
        key2 = active && updateTextSwitch,
    ) {
        mutableStateOf(
            TextFieldValue(
                text = unit.segment.text,
                selection = TextRange(unit.segment.text.length),
            ),
        )
    }
    val spellingErrorRanges = spellingErrors.map { it.indices }
    LaunchedEffect(spellingErrorRanges, active) {
        value = value.copy(
            annotatedString = buildAnnotatedString {
                append(value.text)
                if (active) {
                    for (range in spellingErrorRanges) {
                        runCatching {
                            addStyle(
                                SpanStyle(
                                    color = Color.Red,
                                    textDecoration = TextDecoration.Underline,
                                ),
                                start = range.first,
                                end = (range.last + 1).coerceAtMost(length),
                            )
                        }
                    }
                }
            },
        )
    }

    SuggestCorrectionsForTextFieldContextMenu(
        active = active,
        spellingErrors = spellingErrors,
        onSuggestionAccepted = { word, range ->
            val newText = value.text.replaceRange(range = range, replacement = word)
            value = TextFieldValue(newText)
            onTextChanged(newText)
        },
        onAddToGlossary = { word ->
            onAddToGlossary(word)
        },
        onIgnoreWord = { word ->
            onIgnoreWord(word)
        },
    ) {
        BasicTextField(
            modifier = Modifier.fillMaxWidth()
                .focusRequester(focusRequester)
                .onFocusChanged {
                    if (it.hasFocus) {
                        onStartEditing()
                    }
                },
            enabled = enabled,
            textStyle = MaterialTheme.typography.caption.copy(color = Color.White),
            cursorBrush = SolidColor(Color.White),
            value = value,
            onValueChange = {
                value = value.copy(
                    annotatedString = it.annotatedString,
                    selection = it.selection,
                )
                onTextChanged(it.text)
            },
        )
    }
}
