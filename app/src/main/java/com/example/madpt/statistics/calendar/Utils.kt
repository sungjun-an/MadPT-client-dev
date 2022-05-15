package com.example.madpt.statistics.calendar

fun generateMonth(): ArrayList<MonthData> {
    val list = arrayListOf<MonthData>()

    list.add(MonthData(System.currentTimeMillis(), 200.0,300.0))
    list.add(MonthData(System.currentTimeMillis()+60*60*60*1000, 200.0,300.0))
    list.add(MonthData(System.currentTimeMillis()+60*60*60*2000, 150.0,123.0))
    list.add(MonthData(System.currentTimeMillis()+60*60*60*500, 400.0,532.0))
    list.add(MonthData(System.currentTimeMillis()+60*60*60*2500, 120.0,113.0))
    list.add(MonthData(System.currentTimeMillis()+60*60*60*1500, 340.0,422.0))

    return list
}