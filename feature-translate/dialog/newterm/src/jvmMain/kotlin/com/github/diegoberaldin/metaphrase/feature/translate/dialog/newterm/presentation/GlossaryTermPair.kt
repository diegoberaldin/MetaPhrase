package com.github.diegoberaldin.metaphrase.feature.translate.dialog.newterm.presentation

/**
 * Glossary term pair.
 *
 * @property sourceLemma lemma in the source language
 * @property targetLemma lemma in the target language
 * @constructor Create [GlossaryTermPair]
 */
data class GlossaryTermPair(
    val sourceLemma: String,
    val targetLemma: String,
)