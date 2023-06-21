package com.github.diegoberaldin.metaphrase.domain.mt.repository.datasource.mymemory.dto

import kotlinx.serialization.Serializable

@Serializable
data class ServiceResponse(
    val matches: List<ServiceMatch> = emptyList(),
    val quotaFinished: Boolean = false,
    val responseStatus: String = "",
)
