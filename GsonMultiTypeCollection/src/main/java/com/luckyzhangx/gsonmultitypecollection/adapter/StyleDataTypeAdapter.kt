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

    override fun <T : Any?> create(gson: Gson?, type: TypeToken<T>?): TypeAdapter<T>? {
        if (!StyleData::class.java.isAssignableFrom(type!!.rawType))
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

        if (provider == null)
            throw IllegalStateException("")

        if (provider != null) {
            val delegate = gson!!.getDelegateAdapter(this, type)
            return object : TypeAdapter<T>() {
                override fun write(out: JsonWriter?, value: T) {
                    delegate.write(out, value)
                }

                override fun read(`in`: JsonReader?): T {
                    val jsonElement = Streams.parse(`in`)

                    val tempData = jsonElement.asJsonObject.get("data")

                    jsonElement.asJsonObject.remove("data")

                    val data: T = delegate.fromJsonTree(jsonElement)
                    if (data is StyleData<*>) {
                        val clazz = provider!!.getTypeToken(data.style)
                        try {
                            // todo: 加上类型判断？
                            data::data.set(Gson().fromJson(tempData, clazz))
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }
                    return data
                }
            }
        }
        return null
    }
}

private class StyleClassProvider(val clazz: Type,
                                 val map: Map<String, Class<*>>) {
    fun getTypeToken(style: String): Class<*>? {
        return map[style]
    }
}