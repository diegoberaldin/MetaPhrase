---
title: TranslationUnit
---
//[MetaPhrase](../../../index.html)/[com.github.diegoberaldin.metaphrase.domain.project.data](../index.html)/[TranslationUnit](index.html)



# TranslationUnit



[jvm]\
data class [TranslationUnit](index.html)(val segment: [SegmentModel](../-segment-model/index.html), val original: [SegmentModel](../-segment-model/index.html)? = null, val similarity: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) = 0, val origin: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = &quot;&quot;)

A pair of a source segment and a target segment with the same key is a translation unit. If this is a TM match, it has a similarity rate to another translation unit, based on the distance between the respective source segments.



## Constructors


| | |
|---|---|
| [TranslationUnit](-translation-unit.html) | [jvm]<br>constructor(segment: [SegmentModel](../-segment-model/index.html), original: [SegmentModel](../-segment-model/index.html)? = null, similarity: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) = 0, origin: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = &quot;&quot;)<br>Create [TranslationUnit](index.html) |


## Properties


| Name | Summary |
|---|---|
| [origin](origin.html) | [jvm]<br>val [origin](origin.html): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)<br>origin if this is a match coming from the translation memory |
| [original](original.html) | [jvm]<br>val [original](original.html): [SegmentModel](../-segment-model/index.html)? = null<br>source segment |
| [segment](segment.html) | [jvm]<br>val [segment](segment.html): [SegmentModel](../-segment-model/index.html)<br>target segment |
| [similarity](similarity.html) | [jvm]<br>val [similarity](similarity.html): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) = 0<br>similarity rate |

