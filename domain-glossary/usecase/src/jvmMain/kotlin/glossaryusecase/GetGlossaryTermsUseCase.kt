package glossaryusecase

import glossarydata.GlossaryTermModel

interface GetGlossaryTermsUseCase {
    suspend operator fun invoke(message: String, lang: String): List<GlossaryTermModel>
}
