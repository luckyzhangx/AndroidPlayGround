package com.luckyzhangx.gsonmultitypecollection.data

import com.luckyzhangx.gsonmultitypecollection.adapter.StyleClassProvider

interface Electronic

class SwitchElectronic(val state: Boolean) : Electronic
class Bulb(val light: Int) : Electronic

object ElectronicStyleProvider : StyleClassProvider<Electronic, ElectronicStyleData>(
        ElectronicStyleData::class.java,
        mapOf(
                "switch" to SwitchElectronic::class.java,
                "bulb" to Bulb::class.java
        )
)

object ConsoleStyleProvider : StyleClassProvider<Console, ConsoleStyleData>(
        ConsoleStyleData::class.java,
        mapOf(
                "switch" to Switch::class.java,
                "xbox" to Xbox::class.java,
                "ps4" to Ps4::class.java
        )
)