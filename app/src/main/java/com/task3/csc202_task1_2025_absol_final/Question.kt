package com.task3.csc202_task1_2025_absol_final

import androidx.annotation.StringRes

data class Question(@StringRes val textResId: Int, val answer: Boolean, var isAnswered: Boolean = false, var isCheater: Boolean = false)