package com.levox.whackamole.data

import com.levox.whackamole.domain.repository.GameRepository
import kotlin.random.Random

class GameRepositoryImpl : GameRepository {

    override fun generateMolePosition(): Int {
        return Random(System.nanoTime()).nextInt(0, HOLE_AMOUNT)
    }

    private companion object {

        private const val HOLE_AMOUNT = 9
    }
}