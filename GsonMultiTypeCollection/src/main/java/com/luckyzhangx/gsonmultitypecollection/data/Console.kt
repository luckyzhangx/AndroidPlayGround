package com.luckyzhangx.gsonmultitypecollection.data

interface Console


object ConsoleStyle {
    const val SWITCH = "switch"
    const val XBOX = "xbox"
    const val PS4 = "ps4"

}

class Switch(
        val screenSize: Int
) : Console

class Xbox(
        val themeElliteController: String
) : Console

class Ps4(
        val name: String
) : Console