package com.github.diegoberaldin.metaphrase.domain.tm.usecase

interface ImportTmxUseCase {
    suspend operator fun invoke(path: String)
}

