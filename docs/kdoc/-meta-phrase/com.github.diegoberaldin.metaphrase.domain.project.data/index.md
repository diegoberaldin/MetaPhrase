---
title: com.github.diegoberaldin.metaphrase.domain.project.data
---
//[MetaPhrase](../../index.html)/[com.github.diegoberaldin.metaphrase.domain.project.data](index.html)



# Package-level declarations



## Types


| Name | Summary |
|---|---|
| [ProjectModel](-project-model/index.html) | [jvm]<br>data class [ProjectModel](-project-model/index.html)(val id: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) = 0, val name: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = &quot;&quot;) |
| [RecentProjectModel](-recent-project-model/index.html) | [jvm]<br>data class [RecentProjectModel](-recent-project-model/index.html)(val id: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) = 0, val path: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = &quot;&quot;, val name: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = &quot;&quot;) |
| [ResourceFileType](-resource-file-type/index.html) | [jvm]<br>enum [ResourceFileType](-resource-file-type/index.html) : [Enum](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-enum/index.html)&lt;[ResourceFileType](-resource-file-type/index.html)&gt; |
| [SegmentModel](-segment-model/index.html) | [jvm]<br>data class [SegmentModel](-segment-model/index.html)(val id: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) = 0, val text: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = &quot;&quot;, val key: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = &quot;&quot;, val translatable: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = true) |
| [TranslationUnit](-translation-unit/index.html) | [jvm]<br>data class [TranslationUnit](-translation-unit/index.html)(val segment: [SegmentModel](-segment-model/index.html), val original: [SegmentModel](-segment-model/index.html)? = null, val similarity: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) = 0, val origin: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = &quot;&quot;) |
| [TranslationUnitTypeFilter](-translation-unit-type-filter/index.html) | [jvm]<br>enum [TranslationUnitTypeFilter](-translation-unit-type-filter/index.html) : [Enum](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-enum/index.html)&lt;[TranslationUnitTypeFilter](-translation-unit-type-filter/index.html)&gt; |

