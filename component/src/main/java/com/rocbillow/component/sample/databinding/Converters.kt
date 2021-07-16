package com.rocbillow.component.sample.databinding

import androidx.databinding.InverseMethod
import kotlin.math.round


object Converters {

    @InverseMethod("stringToNumberOfSets")
    @JvmStatic
    fun numberOfSetsToString(
        numberOfSetsWrapper: NumberOfSetsWrapper,
        numberOfSets: NumberOfSetsWrapper
    ): String =
        "${numberOfSets.numberOfSetsElapsed + 1}/${numberOfSets.numberOfSetsTotal}"


    @JvmStatic
    fun stringToNumberOfSets(
        numberOfSetsWrapper: NumberOfSetsWrapper,
        value: String
    ): NumberOfSetsWrapper = try {
        numberOfSetsWrapper.numberOfSetsTotal = value.toInt()
        numberOfSetsWrapper
    } catch (e: NumberFormatException) {
        numberOfSetsWrapper
    }

    @JvmStatic
    fun fromTenthsToSeconds(tenths: Int): String {
        return if (tenths < 600) {
            String.format("%.1f", tenths / 10.0)
        } else {
            val minutes = (tenths / 10) / 60
            val seconds = (tenths / 10) % 60
            String.format("%d:%02d", minutes, seconds)
        }
    }

    @JvmStatic
    fun cleanSecondsString(seconds: String): Int {
        val filteredValue = seconds.replace(Regex("""[^\d:.]"""), "")
        if (filteredValue.isEmpty()) return 0
        val elements: List<Int> =
            filteredValue.split(":").map { it -> round(it.toDouble()).toInt() }

        var result: Int
        return when {
            elements.size > 2 -> 0
            elements.size > 1 -> {
                result = elements[0] * 60
                result += elements[1]
                result * 10
            }
            else -> elements[0] * 10
        }
    }
}