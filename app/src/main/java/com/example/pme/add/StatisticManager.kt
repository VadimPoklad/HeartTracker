package com.example.pme.add

data class StatisticManager(val pulse: StatisticValues, val sys: StatisticValues, val dia: StatisticValues) {
    data class StatisticValues(val max: Int, val min: Int, val avg: Int)

    fun toNestedStringList(): List<List<String>> {
        return listOf(
            listOf("Max pulse", pulse.max.toString()),
            listOf("Min pulse", pulse.min.toString()),
            listOf("Ave pulse", pulse.avg.toString()),
            listOf("Max systolic", sys.max.toString()),
            listOf("Min systolic", sys.min.toString()),
            listOf("Ave systolic", sys.avg.toString()),
            listOf("Max diastolic", dia.max.toString()),
            listOf("Min diastolic", dia.min.toString()),
            listOf("Ave diastolic", dia.avg.toString())
        )
    }
}