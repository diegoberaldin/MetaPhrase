package com.github.diegoberaldin.metaphrase.domain.glossary.usecase

import com.github.diegoberaldin.metaphrase.domain.glossary.data.GlossaryTermModel

interface GetGlossaryTermsUseCase {
    suspend operator fun invoke(message: String, lang: String): List<GlossaryTermModel>
}
