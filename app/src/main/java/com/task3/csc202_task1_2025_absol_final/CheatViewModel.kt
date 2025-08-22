package com.task3.csc202_task1_2025_absol_final

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel

const val PLAYER_CHEATED = "CURRENT_INDEX_KEY"

class CheatViewModel(private val savedStateHandle: SavedStateHandle): ViewModel() {



    private val playerCheated: Boolean = false

    var isCheater: Boolean
        get() = savedStateHandle.get(PLAYER_CHEATED) ?: false
        set(value) = savedStateHandle.set(PLAYER_CHEATED, value)

}