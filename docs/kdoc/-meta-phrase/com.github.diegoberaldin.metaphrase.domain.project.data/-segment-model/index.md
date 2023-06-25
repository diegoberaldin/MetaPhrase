---
title: SegmentModel
---
//[MetaPhrase](../../../index.html)/[com.github.diegoberaldin.metaphrase.domain.project.data](../index.html)/[SegmentModel](index.html)



# SegmentModel



[jvm]\
data class [SegmentModel](index.html)(val id: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) = 0, val text: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = &quot;&quot;, val key: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = &quot;&quot;, val translatable: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = true)

Segment model. A segment belongs to a specific language within a specific project.



## Constructors


| | |
|---|---|
| [SegmentModel](-segment-model.html) | [jvm]<br>constructor(id: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) = 0, text: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = &quot;&quot;, key: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = &quot;&quot;, translatable: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = true)<br>Create [SegmentModel](index.html) |


## Properties


| Name | Summary |
|---|---|
| [id](id.html) | [jvm]<br>val [id](id.html): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) = 0<br>segment ID |
| [key](key.html) | [jvm]<br>val [key](key.html): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)<br>segment identifier (key) |
| [text](text.html) | [jvm]<br>val [text](text.html): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)<br>segment text |
| [translatable](translatable.html) | [jvm]<br>val [translatable](translatable.html): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = true<br>indicates whether this is a translatable or source-only segment |

