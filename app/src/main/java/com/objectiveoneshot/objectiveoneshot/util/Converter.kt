package com.objectiveoneshot.objectiveoneshot.util

import java.util.Calendar

fun getCurrentDate() : Long {
    val currentTime = Calendar.getInstance()
    currentTime.set(Calendar.HOUR_OF_DAY, 0)
    currentTime.set(Calendar.MINUTE, 0)
    currentTime.set(Calendar.SECOND, 0)
    currentTime.set(Calendar.MILLISECOND,0)
    return currentTime.timeInMillis
}