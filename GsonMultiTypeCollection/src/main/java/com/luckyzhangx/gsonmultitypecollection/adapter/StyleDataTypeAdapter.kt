package com.luckyzhangx.gsonmultitypecollection.adapter

import com.google.gson.Gson
import com.google.gson.TypeAdapter
import com.google.gson.TypeAdapterFactory
import com.google.gson.internal.Streams
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter
import com.luckyzhangx.gsonmultitypecollection.anno.StyleClazzMap
import com.luckyzhangx.gsonmultitypecollection.data.StyleData
import java.lang.reflect.Type

class StyleDataTypeFactory : TypeAdapterFactory {
    private val provider = mutableMapOf<Type, StyleClassProvider>()

    private fun registerProvider(styleProvider: StyleClassProvider) {
        provider[styleProvider.clazz] = styleProvider
    }

    override fun <T : Any?> create(gson: Gson, type: TypeToken<T>): TypeAdapter<T>? {

        if (!StyleData::class.java.isAssignableFrom(type.rawType))
            return null

        var provider = provider[type.rawType]

        if (provider == null) {
            val anno =
                    type.rawType.getAnnotation(com.luckyzhangx.gsonmultitypecollection.anno.StyleClazzMap::class.java)
                            ?: throw IllegalStateException("${type.rawType}没有定义${StyleClazzMap::class.java}")
            val map = mutableMapOf<String, Class<*>>().apply {
                anno.styleClazz.forEach {
                    this[it.style] = it.clazz.java
                }
            }

            registerProvider(StyleClassProvider(type.rawType, map).apply {
                provider = this
            })
        }

        val delegate = gson.getDelegateAdapter(this, type)

        return object : TypeAdapter<T>() {
            override fun write(out: JsonWriter?, value: T) {
                delegate.write(out, value)
            }

            override fun read(`in`: JsonReader?): T {
                val jsonElement = Streams.parse(`in`)

                val tempData = jsonElement.asJsonObject.remove("data")

                val styleData: T = delegate.fromJsonTree(jsonElement)
                if (styleData is StyleData<*>) {
                    if (styleData.style.isNullOrBlank()) {
                        throw IllegalStateException("style 数据没有定义 style 类型：$jsonElement")
                    }
                    val clazz = provider!!.getTypeToken(styleData.style ?: "")
                            ?: throw java.lang.IllegalStateException("无法获取 $type style:${styleData.style} 对应的数据类型，" +
                                    "\n请确认 $type 的 ${StyleClazzMap::class.java.simpleName} 定义了 ${styleData.style} ")

                    try {
                        // todo: 加上类型判断？
                        styleData::data.set(gson.fromJson(tempData, clazz))
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
                return styleData
            }
        }
    }

}

private class StyleClassProvider(val clazz: Type,
                                 val map: Map<String, Class<*>>) {
    fun getTypeToken(style: String): Class<*>? {
        return map[style]
    }
}