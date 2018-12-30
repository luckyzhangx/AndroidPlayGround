package com.luckyzhangx.gsonmultitypecollection.data

import com.google.gson.annotations.JsonAdapter
import com.luckyzhangx.gsonmultitypecollection.adapter.StyleDataTypeFactory
import com.luckyzhangx.gsonmultitypecollection.anno.StyleClazz
import com.luckyzhangx.gsonmultitypecollection.anno.StyleClazzMap
import com.luckyzhangx.gsonmultitypecollection.data.ElectronicStyle.BULB
import com.luckyzhangx.gsonmultitypecollection.data.ElectronicStyle.SWITCH

interface Electronic

object ElectronicStyle {
    const val BULB = "bulb"
    const val SWITCH = "switch"
}

@StyleClazzMap(
        StyleClazz(SWITCH, SwitchElectronic::class),
        StyleClazz(BULB, Bulb::class)
)

@JsonAdapter(StyleDataTypeFactory::class)
class ElectronicStyleData(override val style: String,
                          override var data: Electronic?,
                          val category: String) : StyleData<Electronic>

class SwitchElectronic(val state: Boolean) : Electronic
class Bulb(val light: Int) : Electronic