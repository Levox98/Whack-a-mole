package com.levox.whackamole.domain.usecases

import com.levox.whackamole.domain.repository.GameRepository

class GenerateMolePositionUseCase(
    private val repository: GameRepository
) {

    operator fun invoke() = repository.generateMolePosition()
}