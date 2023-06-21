package com.github.diegoberaldin.metaphrase.domain.mt.repository.datasource.mymemory.dto

import kotlinx.serialization.Serializable

@Serializable
data class ServiceMatch(
    val id: String,
    val segment: String,
    val translation: String,
)
