package com.luckyzhangx.gsonmultitypecollection

import com.google.gson.Gson
import com.luckyzhangx.gsonmultitypecollection.data.*

class DataContainer(
        val consoleList: List<ConsoleStyleData>,
        val electronicList: List<ElectronicStyleData>
)

fun main(args: Array<String>) {
    val consoleList = listOf(
            ConsoleStyleData("switch", Switch(10)),
            ConsoleStyleData("xbox", Xbox("bioshock")),
            ConsoleStyleData("ps4", Ps4("monster hunter"))
    )

    val eList = listOf(
            ElectronicStyleData("switch", SwitchElectronic(true), ""),
            ElectronicStyleData("bulb", Bulb(10), "")
    )

    val dataContainer = DataContainer(consoleList, eList)

    val gson = Gson()

    val json = gson.toJson(dataContainer)

    val parsedDataContainer = gson.fromJson<DataContainer>(json, DataContainer::class.java)

    parsedDataContainer.apply {

        this@apply.consoleList.forEach {
            println(it.data)
        }

        electronicList.forEach {
            println(it.data)
        }
    }

}