package com.luckyzhangx.gsonmultitypecollection.data

abstract class StyleData<DATA>(
        val style: String,
        var data: DATA?
)

class ConsoleStyleData(style: String,
                       data: Console) : StyleData<Console>(style, data)

class ElectronicStyleData(style: String,
                          data: Electronic,
                          val category: String) : StyleData<Electronic>(style, data)