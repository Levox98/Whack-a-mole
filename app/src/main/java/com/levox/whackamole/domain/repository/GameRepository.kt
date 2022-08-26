package com.levox.whackamole.domain.repository

interface GameRepository {

    fun generateMolePosition(): Int
}