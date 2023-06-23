---
title: StatisticsItem
---
//[MetaPhrase](../../../index.html)/[com.github.diegoberaldin.metaphrase.feature.projectsdialog.statistics.presentation](../index.html)/[StatisticsItem](index.html)



# StatisticsItem

interface [StatisticsItem](index.html)

Statistics row item.



#### Inheritors


| |
|---|
| [Divider](-divider/index.html) |
| [Header](-header/index.html) |
| [LanguageHeader](-language-header/index.html) |
| [TextRow](-text-row/index.html) |
| [BarChartRow](-bar-chart-row/index.html) |


## Types


| Name | Summary |
|---|---|
| [BarChartRow](-bar-chart-row/index.html) | [jvm]<br>data class [BarChartRow](-bar-chart-row/index.html)(val title: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = &quot;&quot;, val value: [Float](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-float/index.html) = 0.0f) : [StatisticsItem](index.html)<br>Row with a title and a value represented as a bar chart. |
| [Divider](-divider/index.html) | [jvm]<br>object [Divider](-divider/index.html) : [StatisticsItem](index.html)<br>Divider between rows |
| [Header](-header/index.html) | [jvm]<br>data class [Header](-header/index.html)(val title: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = &quot;&quot;) : [StatisticsItem](index.html)<br>Section header. |
| [LanguageHeader](-language-header/index.html) | [jvm]<br>data class [LanguageHeader](-language-header/index.html)(val name: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = &quot;&quot;) : [StatisticsItem](index.html)<br>Language header. |
| [TextRow](-text-row/index.html) | [jvm]<br>data class [TextRow](-text-row/index.html)(val title: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = &quot;&quot;, val value: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = &quot;&quot;) : [StatisticsItem](index.html)<br>Row with a title and a value represented as a string. |

