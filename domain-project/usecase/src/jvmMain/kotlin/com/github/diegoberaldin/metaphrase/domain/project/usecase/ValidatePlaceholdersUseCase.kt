package com.github.diegoberaldin.metaphrase.domain.project.usecase

import com.github.diegoberaldin.metaphrase.domain.project.data.SegmentModel

/**
 * Contract for the placeholder validation use case.
 */
interface ValidatePlaceholdersUseCase {
    /**
     * Result of the validation.
     */
    sealed interface Output {
        /**
         * Case where no errors were detected.
         */
        object Valid : Output

        /**
         * Case where some errors were detected.
         *
         * @property keys list of the keys of the messages where errors were detected
         * @constructor Create [Invalid]
         */
        data class Invalid(
            val keys: List<String> = emptyList(),
        ) : Output
    }

    suspend operator fun invoke(pairs: List<Pair<SegmentModel, SegmentModel>>): Output
}
