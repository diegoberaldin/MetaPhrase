---
title: CustomTextField
---
//[MetaPhrase](../../index.html)/[com.github.diegoberaldin.metaphrase.core.common.ui.components](index.html)/[CustomTextField](-custom-text-field.html)



# CustomTextField



[jvm]\




@Composable



fun [CustomTextField](-custom-text-field.html)(modifier: Modifier = Modifier, label: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = &quot;&quot;, hint: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = &quot;&quot;, enabled: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = true, secure: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false, labelColor: Color = Color.White, backgroundColor: Color = Color.White, textColor: Color = Color.Black, labelExtraSpacing: Dp = 0.dp, labelStyle: TextStyle = MaterialTheme.typography.caption, value: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), singleLine: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false, onValueChange: ([String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)) -&gt; [Unit](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html), endButton: @Composable() -&gt; [Unit](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)? = null)



Custom text field.



#### Parameters


jvm

| | |
|---|---|
| modifier | Modifier |
| label | Label to be shown above the field |
| hint | Hint or placeholder |
| enabled | Enabled/disabled flag |
| secure | Secure flag |
| labelColor | Label color |
| backgroundColor | Background color |
| textColor | Text color |
| labelExtraSpacing | Label extra spacing |
| labelStyle | Label style |
| value | Initial value |
| singleLine | Toggle for single line fields |
| onValueChange | On value change callback |
| endButton | Button to be displayed on the right side |




