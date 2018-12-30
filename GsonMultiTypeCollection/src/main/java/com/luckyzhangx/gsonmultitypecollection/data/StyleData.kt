package com.luckyzhangx.gsonmultitypecollection.data

import com.google.gson.annotations.JsonAdapter
import com.luckyzhangx.gsonmultitypecollection.adapter.StyleDataTypeFactory
import com.luckyzhangx.gsonmultitypecollection.anno.StyleClazzMap
import com.luckyzhangx.gsonmultitypecollection.anno.StyleClazz

abstract class StyleData<DATA>(
        val style: String,
        var data: DATA?
)