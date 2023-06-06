package glossary.usecase

import data.GlossaryTermModel

interface GetGlossaryTermsUseCase {
    suspend operator fun invoke(message: String, lang: String): List<GlossaryTermModel>
}
