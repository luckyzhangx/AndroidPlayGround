package com.luckyzhangx.gsonmultitypecollection.data

import com.google.gson.annotations.JsonAdapter
import com.luckyzhangx.gsonmultitypecollection.adapter.StyleDataTypeFactory
import com.luckyzhangx.gsonmultitypecollection.anno.StyleClazz
import com.luckyzhangx.gsonmultitypecollection.anno.StyleClazzMap
import com.luckyzhangx.gsonmultitypecollection.data.ConsoleStyle.PS4
import com.luckyzhangx.gsonmultitypecollection.data.ConsoleStyle.SWITCH
import com.luckyzhangx.gsonmultitypecollection.data.ConsoleStyle.XBOX

interface Console

object ConsoleStyle {
    const val SWITCH = "switch"
    const val XBOX = "xbox"
    const val PS4 = "ps4"

}

@StyleClazzMap(
        StyleClazz(SWITCH, Switch::class),
        StyleClazz(XBOX, Xbox::class),
        StyleClazz(PS4, Ps4::class))
@JsonAdapter(StyleDataTypeFactory::class)
class ConsoleStyleData : StyleData<Console>()

class Switch(
        val screenSize: Int
) : Console

class Xbox(
        val themeElliteController: String
) : Console

class Ps4(
        val name: String
) : Console